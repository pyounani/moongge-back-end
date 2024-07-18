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

    List<GetLikeList> findByPostIdAndGroupCode(PostEntity postId, GroupEntity groupCode);

    List<GetLikeList> findByPostId(PostEntity postId);

    Optional<LikeEntity> deleteByGroupCode(GroupEntity groupCode);

    List<LikeEntity> findByUserId(UserEntity userId);

    Optional<LikeEntity> findByGroupCodeAndUserIdAndPostId(GroupEntity groupCode, UserEntity userId, PostEntity postId);

    Optional<LikeEntity> deleteByGroupCodeAndUserIdAndPostId(GroupEntity groupCode, UserEntity userId, PostEntity postId);

    Long countByGroupCodeAndPostId(GroupEntity groupCode, PostEntity postId);

    Long countByUserId(UserEntity userId);


}
