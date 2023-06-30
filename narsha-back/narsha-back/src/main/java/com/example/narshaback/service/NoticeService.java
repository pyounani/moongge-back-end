package com.example.narshaback.service;

import com.example.narshaback.base.dto.notice.CreateNoticeDTO;
import com.example.narshaback.entity.NoticeEntity;
import com.example.narshaback.base.projection.notice.GetNotice;
import com.example.narshaback.base.projection.notice.GetRecentNotice;

import java.util.List;
import java.util.Optional;

public interface NoticeService {

    Boolean createNotice(CreateNoticeDTO createNoticeDTO);
    List<GetNotice> getNoticeList(String GroupId);

    Optional<NoticeEntity> getNoticeDetail(Integer NoticeId);

    Optional<GetRecentNotice> getRecentNoticeOne(String groupId);
}
