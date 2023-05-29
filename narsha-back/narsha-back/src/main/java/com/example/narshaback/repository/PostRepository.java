package com.example.narshaback.repository;

import com.example.narshaback.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    PostEntity findById(String postId);
}
