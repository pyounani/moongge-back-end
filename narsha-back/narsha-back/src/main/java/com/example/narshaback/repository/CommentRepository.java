package com.example.narshaback.repository;

import com.example.narshaback.entity.CommentEntity;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.base.projection.comment.GetComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<GetComment> findByPostIdAndGroupCode(PostEntity postId, GroupEntity groupCode);

    List<GetComment> findByPostId(PostEntity postId);


    Optional<CommentEntity> deleteByGroupCode(GroupEntity groupCode);

}
