package com.example.narshaback.service;

import com.example.narshaback.dto.CreateNoticeDTO;
import com.example.narshaback.projection.GetNoticeList;

import java.util.List;

public interface NoticeService {

    Boolean createNotice(CreateNoticeDTO createNoticeDTO);
    List<GetNoticeList> getNoticeList(String GroupId);
}
