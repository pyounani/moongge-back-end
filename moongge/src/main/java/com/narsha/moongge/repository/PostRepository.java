package com.narsha.moongge.repository;

import com.narsha.moongge.base.projection.post.GetMainPost;
import com.narsha.moongge.base.projection.post.GetOneUserPost;
import com.narsha.moongge.base.projection.post.GetUserPost;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    List<GetUserPost> findByGroupCode(GroupEntity groupCode);

    List<GetOneUserPost> findByUserOrderByCreateAtDesc(UserEntity userId);
    Optional<PostEntity> findByPostIdAndGroupCode(Integer postId, GroupEntity groupCode);

    Optional<PostEntity> findByPostId(Integer postId);

    List<GetMainPost> findByGroupCodeAndCreateAtBetweenOrderByCreateAtDesc(GroupEntity groupCode, LocalDateTime startTime, LocalDateTime endTime);
}
