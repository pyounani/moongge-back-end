package com.narsha.moongge.repository;

import com.narsha.moongge.base.projection.notice.GetNotice;
import com.narsha.moongge.base.projection.notice.GetRecentNotice;
import com.narsha.moongge.entity.NoticeEntity;
import com.narsha.moongge.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity,Integer> {
    List<GetNotice> findByGroupCode(GroupEntity GroupId);
    Optional<NoticeEntity> findByNoticeId(Integer NoticeId);

    Optional<GetRecentNotice> findTopByGroupCodeOrderByCreateAtDesc(GroupEntity GroupId);

    Optional<NoticeEntity> deleteByGroupCode(GroupEntity groupCode);
}
