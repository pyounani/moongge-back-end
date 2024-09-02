package com.narsha.moongge.service;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.like.LikeDTO;
import com.narsha.moongge.base.exception.*;
import com.narsha.moongge.entity.LikeEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService{

    private static final Long MIN_LIKES_REQUIRED = 10L;

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    /**
     * 좋아요 생성하기
     */
    @Override
    @Transactional
    public Integer createLike(String userId, Integer postId) {

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, user.getGroup())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        // 좋아요가 이미 생성된 경우 예외처리
        if (likeRepository.existsByPostAndUser(post, user)) {
            throw new LikeAlreadyExistsException(ErrorCode.LIKE_ALREADY_EXISTS);
        }

        LikeEntity like = LikeEntity.builder()
                .group(user.getGroup())
                .post(post)
                .user(user)
                .build();

        LikeEntity savedLike = likeRepository.save(like);

        return savedLike.getLikeId();
    }

    /**
     * 특정 포스트에 좋아요 누른 유저 목록 가져오기
     */
    @Override
    public List<LikeDTO> getLikeList(String userId, Integer postId) {

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, user.getGroup())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        List<LikeEntity> likeList = likeRepository.findLikesWithUserByPost(post);

        return likeList.stream()
                .map(LikeDTO::mapToLikeDTO)
                .collect(Collectors.toList());
    }

    /**
     * 좋아요 취소하기
     */
    @Transactional
    @Override
    public LikeDTO deleteLike(String userId, Integer postId){

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, user.getGroup())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        LikeEntity like = likeRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new LikeNotFoundException(ErrorCode.LIKE_NOT_FOUND));

        likeRepository.delete(like);

        return LikeDTO.mapToLikeDTO(like);
    }

    /**
     * 유저가 특정 포스트에 좋아요 누른 여부
     */
    @Override
    public Boolean checkLikePost(String userId, Integer postId) {

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, user.getGroup())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        return likeRepository.existsByPostAndUser(post, user);
    }

    /**
     * 특정 포스트에 좋아요 갯수 가져오기
     */
    @Override
    public Long countLike(String userId, Integer postId){

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, user.getGroup())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        return likeRepository.countByPost(post);
    }

    /**
     * 사용자가 쓴 게시글 중 좋아요 10개가 넘는 글의 여부
     */
    @Override
    public Boolean receiveTenLikes(String userId) {

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        return likeRepository.existsPostWithMinLikesByUser(user, MIN_LIKES_REQUIRED);
    }

    /**
     * 사용자가 좋아요를 10개 이상 달았는지 여부 가져오기
     */
    @Override
    public Boolean giveTenLikes(String userId) {

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Long count = likeRepository.countByUser(user);

        if (count >= MIN_LIKES_REQUIRED) {
            return true;
        }
        return false;
    }
}
