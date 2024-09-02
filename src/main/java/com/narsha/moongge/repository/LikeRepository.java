package com.narsha.moongge.repository;

import com.narsha.moongge.entity.LikeEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.queryDSL.LikeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer>, LikeRepositoryCustom{
    Optional<LikeEntity> findByPostAndUser(PostEntity post, UserEntity user);
    boolean existsByPostAndUser(PostEntity post, UserEntity user);
    Long countByPost(PostEntity post);
    Long countByUser(UserEntity user);
}
