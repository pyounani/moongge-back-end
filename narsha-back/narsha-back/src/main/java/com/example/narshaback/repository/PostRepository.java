package com.example.narshaback.repository;

import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.PostEntity;
<<<<<<< HEAD
import com.example.narshaback.projection.post.GetPostDetail;
=======
import com.example.narshaback.base.projection.post.GetUserPost;
import com.example.narshaback.entity.UserEntity;
>>>>>>> edabb70 ([feat] comment, like 에러 처리 추가)
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
<<<<<<< HEAD
=======
    List<GetUserPost> findByGroupCode(GroupEntity groupCode);
    Optional<PostEntity> findByPostIdAndGroupCode(Integer postId, GroupEntity groupCode);

    Optional<PostEntity> findByPostId(Integer postId);
>>>>>>> edabb70 ([feat] comment, like 에러 처리 추가)
}
