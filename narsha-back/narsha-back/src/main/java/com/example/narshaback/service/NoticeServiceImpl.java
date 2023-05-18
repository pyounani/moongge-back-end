package com.example.narshaback.service;

import com.example.narshaback.dto.CreateNoticeDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.NoticeEntity;
import com.example.narshaback.projection.GetNoticeList;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private final GroupRepository groupRepository;

    @Override
    public Boolean createNotice(CreateNoticeDTO createNoticeDTO) {

        GroupEntity findGroup = groupRepository.findByGroupCode(createNoticeDTO.getGroupId());

        if (findGroup != null){
            NoticeEntity notice = NoticeEntity.builder()
                    .groupId(findGroup)
                    .noticeTitle(createNoticeDTO.getNoticeTitle())
                    .noticeContent(createNoticeDTO.getNoticeContent())
                    .build();
            noticeRepository.save(notice).getId();
            return true;
        } else return false;
    }

    @Override
    public List<GetNoticeList> getNoticeList(String GroupId) {
        GroupEntity group = groupRepository.findByGroupCode(GroupId);
        List<GetNoticeList> noticeList = noticeRepository.findByGroupId(group);

        return noticeList;
    }
}
