package com.example.narshaback.repository;

import com.example.narshaback.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    Optional<PostEntity> findById(Integer postId);
}
