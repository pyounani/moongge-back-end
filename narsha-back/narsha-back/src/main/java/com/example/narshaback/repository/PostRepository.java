package com.example.narshaback.repository;

import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.projection.post.GetUserPost;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<GetUserPost> findByUserGroupId(User_Group userGroupId);
    Optional<PostEntity> findByPostIdAndUserGroupId(Integer postId, User_Group userGroupId);
}
