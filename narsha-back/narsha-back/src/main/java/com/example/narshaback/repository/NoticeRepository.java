package com.example.narshaback.repository;

import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.NoticeEntity;
import com.example.narshaback.projection.notice.GetNotice;
import com.example.narshaback.projection.notice.GetRecentNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity ,Integer> {
    List<GetNotice> findByGroupCode(GroupEntity GroupId);
    Optional<NoticeEntity> findByNoticeId(Integer NoticeId);

    Optional<GetRecentNotice> findTopByGroupCodeOrderByCreateAtDesc(GroupEntity GroupId);
}
