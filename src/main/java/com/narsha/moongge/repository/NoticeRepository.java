package com.narsha.moongge.repository;

import com.narsha.moongge.entity.NoticeEntity;
import com.narsha.moongge.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity,Integer> {

    List<NoticeEntity> findByGroup(GroupEntity group);
    Optional<NoticeEntity> findByNoticeId(Integer noticeId);
    Optional<NoticeEntity> findTopByGroupOrderByCreateAtDesc(GroupEntity group);

}
