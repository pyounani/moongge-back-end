package com.example.narshaback.service;

import com.example.narshaback.dto.CreateNoticeDTO;
import com.example.narshaback.dto.GetNoticeListDTO;

import java.util.List;

public interface NoticeService {

    Boolean createNotice(CreateNoticeDTO createNoticeDTO);
    List<GetNoticeListDTO> getNoticeList(String GroupId);
}
