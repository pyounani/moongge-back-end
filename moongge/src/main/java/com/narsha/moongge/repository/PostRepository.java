package com.narsha.moongge.repository;

import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    List<PostEntity> findByUserOrderByCreateAtDesc(UserEntity user);
    List<PostEntity> findByUser(UserEntity user);
    Optional<PostEntity> findByPostIdAndGroup(Integer postId, GroupEntity group);
    Optional<PostEntity> findByPostId(Integer postId);
    List<PostEntity> findByGroupAndCreateAtBetweenOrderByCreateAtDesc(GroupEntity group, LocalDateTime startTime, LocalDateTime endTime);
}
