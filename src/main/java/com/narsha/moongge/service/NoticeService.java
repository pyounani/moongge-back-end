package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.notice.CreateNoticeDTO;
import com.narsha.moongge.base.dto.notice.NoticeDTO;
import com.narsha.moongge.entity.NoticeEntity;

import java.util.List;
import java.util.Optional;

public interface NoticeService {

    NoticeDTO createNotice(CreateNoticeDTO createNoticeDTO);
    List<NoticeDTO> getNoticeList(String groupCode);
    NoticeDTO getNoticeDetail(String userId, Integer noticeId);
    NoticeDTO getRecentNoticeOne(String groupCode);
}
