package com.narsha.moongge.service;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.comment.CommentDTO;
import com.narsha.moongge.base.dto.like.CreateLikeDTO;
import com.narsha.moongge.base.dto.like.DeleteLikeDTO;
import com.narsha.moongge.base.dto.like.LikeDTO;
import com.narsha.moongge.base.exception.*;
import com.narsha.moongge.base.projection.like.GetLikeList;
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

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    /**
     * 좋아요 생성하기
     */
    @Override
    @Transactional
    public Integer createLike(String groupCode, Integer postId, CreateLikeDTO createLikeDTO) {

        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, group)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        UserEntity user = userRepository.findByUserId(createLikeDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 좋아요가 이미 생성된 경우 예외처리
        likeRepository.findByPostAndUser(post, user).ifPresent(like -> {
            throw new LikeAlreadyExistsException(ErrorCode.LIKE_ALREADY_EXISTS);
        });

        LikeEntity like = LikeEntity.builder()
                .group(group)
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
    public List<LikeDTO> getLikeList(String groupCode, Integer postId) {

        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, group)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        List<LikeEntity> likeList = likeRepository.findByPost(post);

        return likeList.stream()
                .map(LikeDTO::mapToLikeDTO)
                .collect(Collectors.toList());
    }

    /**
     * 좋아요 취소하기
     */
    @Transactional
    @Override
    public LikeDTO deleteLike(String groupCode, Integer postId, DeleteLikeDTO deleteLikeDTO){

        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, group)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        UserEntity user = userRepository.findByUserId(deleteLikeDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        LikeEntity like = likeRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new LikeNotFoundException(ErrorCode.LIKE_NOT_FOUND));

        likeRepository.delete(like);

        return LikeDTO.mapToLikeDTO(like);
    }

    /**
     * 유저가 특정 포스트에 좋아요 누른 여부
     */
    @Override
    public Boolean checkLikePost(String userId, String groupCode, Integer postId) {

        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, group)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Optional<LikeEntity> like = likeRepository.findByPostAndUser(post, user);

        return like.isPresent();
    }

    @Override
    public Long countLike(String groupCode, Integer postId){

        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupCode);
        if(!group.isPresent()){
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
        }

        Optional<PostEntity> post = postRepository.findByPostId(postId);
        // 게시물이 존재하지 않은 경우
        if (!post.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }

        Long like = likeRepository.countByGroupAndPost(group.get(), post.get());

        return like;

    }

    @Override
    public Boolean receiveTenLikes(String userId, String groupCode) {

        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if(!user.isPresent()) {
            throw new UserNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupCode);
        if(!group.isPresent()){
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
        }

        List<PostEntity> userPostList = postRepository.findByUserOrderByCreateAtDesc(user.get());

        for (int i=0;i<userPostList.size();i++){
            PostEntity post = userPostList.get(i);
            Integer postId = post.getPostId();

            Integer count = Math.toIntExact(countLike(groupCode, postId));
            if (count>=10){
                return true;
            }
        }

        return false;

    }

    @Override
    public Long giveTenLikes(String userId) {

        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if(!user.isPresent()) {
            throw new UserNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        Long count = likeRepository.countByUser(user.get());

        return count;
//        if (count>=10)
//            return true;
//        else
//            return false;

    }
}
