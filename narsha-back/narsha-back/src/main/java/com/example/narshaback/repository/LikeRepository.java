package com.example.narshaback.repository;

import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.LikeEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.base.projection.like.GetLikeList;
import com.example.narshaback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    List<GetLikeList> findByPostIdAndGroupCode(PostEntity postId, GroupEntity groupCode);

    List<GetLikeList> findByPostId(PostEntity postId);

    Optional<LikeEntity> deleteByGroupCode(GroupEntity groupCode);

    List<LikeEntity> findByUserId(UserEntity userId);

}
