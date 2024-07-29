package com.narsha.moongge.service;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.like.CreateLikeDTO;
import com.narsha.moongge.base.exception.GroupNotFoundException;
import com.narsha.moongge.base.exception.PostNotFoundException;
import com.narsha.moongge.base.exception.UserNotFoundException;
import com.narsha.moongge.base.projection.like.GetLikeList;
import com.narsha.moongge.entity.LikeEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;

    private final PostRepository postRepository;
    private final GroupRepository groupRepository;

    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Integer createLike(CreateLikeDTO createLikeDTO) {

        Optional<UserEntity> user = userRepository.findByUserId(createLikeDTO.getUserId());
        if(!user.isPresent()) {
            throw new UserNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        Optional<PostEntity> post = postRepository.findByPostId(createLikeDTO.getPostId());
        // 게시물이 존재하지 않은 경우
        if (!post.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }

        Optional<GroupEntity> group = groupRepository.findByGroupCode(createLikeDTO.getGroupCode());
        if(!group.isPresent()){
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
        }

        LikeEntity like = LikeEntity.builder()
                .postId(post.get())
                .groupCode(group.get())
                .userId(user.get())
                .build();

        likeRepository.save(like);

        return likeRepository.save(like).getLikeId();
    }


    @Override
    public List<GetLikeList> getLikeList(Integer postId) {

        Optional<PostEntity> findPost = postRepository.findByPostId(postId);
        // 게시물이 존재하지 않은 경우
        if (!findPost.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }

        List<GetLikeList> likeList = likeRepository.findByPostId(findPost.get());

        return likeList;
    }

    @Override
    public Boolean checkLikePost(String userId, String groupCode, Integer postId) {

        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if(!user.isPresent()) {
            throw new UserNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        Optional<PostEntity> post = postRepository.findByPostId(postId);
        // 게시물이 존재하지 않은 경우
        if (!post.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }

        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupCode);
        if(!group.isPresent()){
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
        }

        Optional<LikeEntity> like = likeRepository.findByGroupCodeAndUserIdAndPostId(group.get(), user.get(), post.get());

        if(!like.isPresent()){
            return false;
        }else {
            return true;
        }
    }

    @Transactional
    @Override
    public String deleteLike(String userId, String groupCode, Integer postId){

        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if(!user.isPresent()) {
            throw new UserNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        Optional<PostEntity> post = postRepository.findByPostId(postId);
        // 게시물이 존재하지 않은 경우
        if (!post.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }

        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupCode);
        if(!group.isPresent()){
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
        }

        Optional<LikeEntity> like = likeRepository.findByGroupCodeAndUserIdAndPostId(group.get(), user.get(), post.get());

        if(like.isPresent()){
            alarmRepository.deleteByLikeId(like.get());
            likeRepository.deleteByGroupCodeAndUserIdAndPostId(group.get(), user.get(), post.get());
        }

        return "success";
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

        Long like = likeRepository.countByGroupCodeAndPostId(group.get(), post.get());

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

        Long count = likeRepository.countByUserId(user.get());

        return count;
//        if (count>=10)
//            return true;
//        else
//            return false;

    }
}
