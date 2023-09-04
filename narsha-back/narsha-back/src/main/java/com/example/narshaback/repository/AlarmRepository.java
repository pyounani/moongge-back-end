package com.example.narshaback.repository;

import com.example.narshaback.base.projection.alarm.GetAlarmList;
import com.example.narshaback.base.projection.comment.GetComment;
import com.example.narshaback.base.projection.like.GetLikeList;
import com.example.narshaback.base.projection.notice.GetNotice;
import com.example.narshaback.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<AlarmEntity, Integer> {
    List<GetAlarmList> findByPostId(PostEntity postId);

    List<GetAlarmList> findByGroupCode(GroupEntity groupCode);

    Optional<AlarmEntity> deleteByLikeId(LikeEntity likeId);

    Optional<AlarmEntity> deleteByGroupCode(GroupEntity groupCode);

}
