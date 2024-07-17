package com.narsha.moongge.repository;

import com.narsha.moongge.base.projection.comment.GetComment;
import com.narsha.moongge.entity.CommentEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.group.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<GetComment> findByPostIdAndGroupCode(PostEntity postId, GroupEntity groupCode);

    List<GetComment> findByPostId(PostEntity postId);

    Optional<CommentEntity> deleteByGroupCode(GroupEntity groupCode);

    Optional<GetComment> findTopByPostIdOrderByCreateAtDesc(PostEntity post);

//    List<GetComment> findByUserId(String userId);
    Long countByUserId(UserEntity userId);

}
