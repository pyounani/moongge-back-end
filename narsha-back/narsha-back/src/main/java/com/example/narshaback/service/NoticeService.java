package com.example.narshaback.service;

import com.example.narshaback.dto.notice.CreateNoticeDTO;
import com.example.narshaback.entity.NoticeEntity;
import com.example.narshaback.projection.notice.GetNotice;

import java.util.List;
import java.util.Optional;

public interface NoticeService {

    Boolean createNotice(CreateNoticeDTO createNoticeDTO);
    List<GetNotice> getNoticeList(String GroupId);

    Optional<NoticeEntity> getNoticeDetail(Integer NoticeId);
}
