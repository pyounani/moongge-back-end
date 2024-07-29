package com.narsha.moongge.repository;


import com.narsha.moongge.base.projection.like.GetLikeList;
import com.narsha.moongge.entity.LikeEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    List<GetLikeList> findByPost(PostEntity post);

    List<LikeEntity> findByUser(UserEntity user);

    Optional<LikeEntity> findByGroupAndUserAndPost(GroupEntity group, UserEntity user, PostEntity post);

    Optional<LikeEntity> deleteByGroupAndUserAndPost(GroupEntity group, UserEntity user, PostEntity post);

    Long countByGroupAndPost(GroupEntity group, PostEntity post);

    Long countByUser(UserEntity user);


}
