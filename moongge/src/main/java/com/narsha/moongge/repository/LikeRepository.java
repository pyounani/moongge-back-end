package com.narsha.moongge.repository;


import com.narsha.moongge.entity.LikeEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    List<LikeEntity> findByPost(PostEntity post);
    Optional<LikeEntity> findByPostAndUser(PostEntity post, UserEntity user);
    List<LikeEntity> findByUser(UserEntity user);
    Long countByPost(PostEntity post);
    Long countByUser(UserEntity user);

}
