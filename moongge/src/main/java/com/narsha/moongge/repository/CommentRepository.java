package com.narsha.moongge.repository;

import com.narsha.moongge.base.projection.comment.GetComment;
import com.narsha.moongge.entity.CommentEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    List<GetComment> findByPostId(PostEntity postId);
    Optional<GetComment> findTopByPostIdOrderByCreateAtDesc(PostEntity post);
    Long countByUserId(UserEntity userId);
}
