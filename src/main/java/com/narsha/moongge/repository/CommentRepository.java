package com.narsha.moongge.repository;

import com.narsha.moongge.entity.CommentEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.repository.queryDSL.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>, CommentRepositoryCustom {
    Long countByPost(PostEntity post);
}
