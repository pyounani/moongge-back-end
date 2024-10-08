package com.narsha.moongge.service;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.post.PostDTO;
import com.narsha.moongge.base.dto.post.UploadPostDTO;
import com.narsha.moongge.base.exception.*;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.PostRepository;
import com.narsha.moongge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostServiceImpl implements PostService {

    private static final String POST_PHOTOS_FOLDER = "post/photos";
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AmazonS3Service amazonS3Service;

    /**
     * 포스트 업로드
     */
    @Override
    @Transactional
    public PostDTO uploadPost(String userId, MultipartFile[] multipartFiles, UploadPostDTO uploadPostDTO) {

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // S3 업로드
        List<String> imageUrls = uploadImagesToS3(multipartFiles);

        // 포스트 엔티티 생성 및 저장
        PostEntity post = PostEntity.builder()
                .content(uploadPostDTO.getContent())
                .imageArray(String.join(",", imageUrls))
                .user(user)
                .group(user.getGroup())
                .build();

        PostEntity savedPost = postRepository.save(post);

        return PostDTO.mapToPostDTO(savedPost);
    }

    /**
     * 포스트 상세 조회하기
     */
    @Override
    public PostDTO getPostDetail(String userId, Integer postId) {

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroupWithWriter(postId, user.getGroup())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        return PostDTO.mapToPostDTO(post);
    }

    /**
     * 유저가 올린 포스트 목록 가져오기
     */
    @Override
    public List<PostDTO> getUserPost(String userId) {

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        List<PostEntity> userPostList = postRepository.findByUserOrderByCreateAtDesc(user);

        return userPostList.stream()
                .map(PostDTO::mapToPostDTO)
                .collect(Collectors.toList());
    }

    /**
     * 유저가 좋아요를 누르지 않은 최신 포스트 목록 가져오기
     */
    @Override
    public List<PostDTO> getMainPost(String userId) {

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.now().minus(24, ChronoUnit.HOURS);

        List<PostEntity> mainPost = postRepository.getMainPost(userId, user.getGroup(), startTime, endTime);

        return mainPost.stream()
                .map(PostDTO::mapToPostDTO)
                .collect(Collectors.toList());
    }

    private List<String> uploadImagesToS3(MultipartFile[] multipartFiles) {
        List<String> imageUrls = new ArrayList<>();

        // S3에 업로드 및 URL 리스트 생성
        for (MultipartFile multipartFile : multipartFiles) {
            String imageUrl = amazonS3Service.uploadFileToS3(multipartFile, POST_PHOTOS_FOLDER);
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

}
