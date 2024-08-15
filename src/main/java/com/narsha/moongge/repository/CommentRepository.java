package com.narsha.moongge.repository;

import com.narsha.moongge.entity.CommentEntity;
import com.narsha.moongge.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    List<CommentEntity> findByPost(PostEntity post);
    Optional<CommentEntity> findTopByPostOrderByCreateAtDesc(PostEntity post);
    Long countByPost(PostEntity post);
}
