package com.example.narshaback.repository;

import com.example.narshaback.base.projection.post.GetMainPost;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.base.projection.post.GetUserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    List<GetUserPost> findByGroupCode(GroupEntity groupCode);
    Optional<PostEntity> findByPostIdAndGroupCode(Integer postId, GroupEntity groupCode);

    Optional<PostEntity> findByPostId(Integer postId);

    Optional<PostEntity> deleteByGroupCode(GroupEntity groupCode);

    List<GetMainPost> findByCreateAtBetween(LocalDateTime startTime, LocalDateTime endTime);

}
