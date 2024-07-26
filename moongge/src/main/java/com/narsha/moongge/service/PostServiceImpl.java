package com.narsha.moongge.service;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.post.PostDTO;
import com.narsha.moongge.base.dto.post.UploadPostDTO;
import com.narsha.moongge.base.exception.*;
import com.narsha.moongge.base.projection.post.GetMainPost;
import com.narsha.moongge.base.projection.post.GetOneUserPost;
import com.narsha.moongge.base.projection.post.GetPostDetail;
import com.narsha.moongge.base.projection.post.GetUserPost;
import com.narsha.moongge.entity.LikeEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.LikeRepository;
import com.narsha.moongge.repository.PostRepository;
import com.narsha.moongge.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
public class PostServiceImpl implements PostService {

    private static final String POST_PHOTOS_FOLDER = "post/photos";
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final LikeRepository likeRepository;
    private final AmazonS3Service amazonS3Service;

    /**
     * 포스트 업로드
     */
    @Override
    @Transactional
    public PostDTO uploadPost(MultipartFile[] multipartFiles, UploadPostDTO uploadPostDTO) {

        // 해당 유저-그룹 찾기
        GroupEntity group = groupRepository.findByGroupCode(uploadPostDTO.getGroupCode())
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        UserEntity user = userRepository.findByUserId(uploadPostDTO.getWriter())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        List<String> imageUrls = uploadImagesToS3(multipartFiles);

        // 포스트 엔티티 생성 및 저장
        PostEntity post = PostEntity.builder()
                .content(uploadPostDTO.getContent())
                .imageArray(String.join(",", imageUrls))
                .user(user)
                .groupCode(group)
                .build();

        PostEntity savedPost = postRepository.save(post);

        return PostDTO.mapToPostDTO(savedPost);
    }

    @Override
    public List<GetUserPost> getUserPost(String groupCode) {
        Optional<GroupEntity> user_group = groupRepository.findByGroupCode(groupCode);
        if(!user_group.isPresent()){
            throw new GroupNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);
        }

        List<GetUserPost> postList = postRepository.findByGroupCode(user_group.get());

        return postList;
    }

    @Override
    public GetPostDetail getPostDetail(Integer postId, String groupCode, String userId) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupCode);

        if(!group.isPresent()){
            throw new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);
        }

        Optional<UserEntity> user = userRepository.findByUserId(userId);

        if(!user.isPresent()){
            throw new LoginIdNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        Optional<PostEntity> post = postRepository.findByPostIdAndGroupCode(postId, group.get());

        if(!post.isPresent()){
            throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
        }

        System.out.println(post);
        if(post.isPresent()) {

            // repository에서 projection으로 반환받아서 못 가져오기에 service 내부에 mapping 코드 필요...
            GetPostDetail res = new GetPostDetail() {
                @Override
                public Integer getId() {
                    return post.get().getPostId();
                }

                @Override
                public String getContent() {
                    return post.get().getContent();
                }

                @Override
                public String getImageArray() {
                    return post.get().getImageArray();
                }

                @Override
                public LocalDateTime getCreateAt() {
                    return post.get().getCreateAt();
                }

                @Override
                public UserEntity getWriter() {
                    return user.get();
                }
            };

            return res;
        } else {
            throw new EntityNotFoundException(String.format("포스트 아이디 %d로 조회되지 않았습니다", postId));
        }
    }

    @Override
    public List<GetMainPost> getMainPost(String userId, String groupCode) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupCode);
        if(group == null) {
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
        }
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if(user == null) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        // 24시간 내 게시물 불러오고
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.now().minus(24, ChronoUnit.HOURS);

        List<GetMainPost> allPost = postRepository.findByGroupCodeAndCreateAtBetweenOrderByCreateAtDesc(group.get(), startTime, endTime);

        // 그 중에서 사용자가 좋아요를 누르지 않은 게시물 보여주기
        List<LikeEntity> userLike = likeRepository.findByUserId(user.get());
        List<Integer> postIdList = userLike.stream()
                .map(likeEntity -> likeEntity.getPostId().getPostId())
                .collect(Collectors.toList());

        List<GetMainPost> nonLikedPost = allPost.stream()
                .filter(post -> !postIdList.contains(post.getPostId()))
                .collect(Collectors.toList());

        return nonLikedPost;
    }

    @Override
    public List<GetOneUserPost> getOneUserPost(String userId) {

        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if(!user.isPresent()){
            throw new LoginIdNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        List<GetOneUserPost> userPostList = postRepository.findByUserOrderByCreateAtDesc(user.get());

        return userPostList;
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
