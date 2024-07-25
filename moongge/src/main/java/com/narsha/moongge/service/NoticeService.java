package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.notice.CreateNoticeDTO;
import com.narsha.moongge.base.dto.notice.NoticeDTO;
import com.narsha.moongge.base.projection.notice.GetRecentNotice;
import com.narsha.moongge.entity.NoticeEntity;

import java.util.List;
import java.util.Optional;

public interface NoticeService {

    NoticeDTO createNotice(String groupCode, CreateNoticeDTO createNoticeDTO);
    List<NoticeDTO> getNoticeList(String GroupId);

    Optional<NoticeEntity> getNoticeDetail(Integer NoticeId);

    Optional<GetRecentNotice> getRecentNoticeOne(String groupId);
}
