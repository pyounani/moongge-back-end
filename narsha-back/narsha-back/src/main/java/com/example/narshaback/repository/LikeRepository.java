package com.example.narshaback.repository;

import com.example.narshaback.entity.LikeEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.projection.like.GetLikeList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    List<GetLikeList> findByPostIdAndUserGroupId(PostEntity postId, User_Group userGroupId);
}
