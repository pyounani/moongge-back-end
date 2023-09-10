package com.example.narshaback.service;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.dto.post.UploadPostDTO;
import com.example.narshaback.base.exception.*;
import com.example.narshaback.base.projection.post.GetMainPost;
import com.example.narshaback.base.projection.post.GetOneUserPost;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.LikeEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.base.projection.post.GetPostDetail;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.base.projection.post.GetUserPost;
import com.example.narshaback.repository.LikeRepository;
import com.example.narshaback.repository.PostRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;

    private final LikeRepository likeRepository;

    @Override
    public Integer uploadPost(UploadPostDTO uploadPostDTO) {
        // 해당 유저-그룹 찾기
        Optional<GroupEntity> group = groupRepository.findByGroupCode(uploadPostDTO.getGroupCode());

        Optional<UserEntity> user = userRepository.findByUserId(uploadPostDTO.getWriter());

        if(!user.isPresent()){
            throw new LoginIdNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        if(!group.isPresent()){
            throw new GroupNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);
        } else {
            PostEntity post = PostEntity.builder()
                    .content(uploadPostDTO.getContent())
                    .imageArray(uploadPostDTO.getImageArray().toString())
                    .user(user.get())
                    .groupCode(group.get())
                    .build();
            PostEntity uploadPost = postRepository.save(post);
            if (uploadPost == null) return null;
            return uploadPost.getPostId();
        }
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
}
