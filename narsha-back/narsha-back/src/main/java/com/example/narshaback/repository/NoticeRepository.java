package com.example.narshaback.repository;

import com.example.narshaback.dto.GetNoticeListDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity ,Integer> {
    List<GetNoticeListDTO> findByGroupId(GroupEntity GroupId);
}
