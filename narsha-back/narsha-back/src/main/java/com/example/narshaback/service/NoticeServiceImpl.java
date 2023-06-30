package com.example.narshaback.service;

import com.example.narshaback.dto.notice.CreateNoticeDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.NoticeEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.projection.notice.GetNotice;
import com.example.narshaback.projection.notice.GetRecentNotice;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.NoticeRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private final GroupRepository groupRepository;

    private final UserRepository userRepository;

    @Override
    public Boolean createNotice(CreateNoticeDTO createNoticeDTO) {

        GroupEntity findGroup = groupRepository.findByGroupCode(createNoticeDTO.getGroupCode());
        UserEntity findUser = userRepository.findByUserId(createNoticeDTO.getWriter());

        if (findGroup != null){
            NoticeEntity notice = NoticeEntity.builder()
                    .groupCode(findGroup)
                    .noticeTitle(createNoticeDTO.getNoticeTitle())
                    .noticeContent(createNoticeDTO.getNoticeContent())
                    .writer(findUser)
                    .build();
            noticeRepository.save(notice).getNoticeId();
            return true;
        } else return false;
    }

    @Override
    public List<GetNotice> getNoticeList(String GroupId) {
        GroupEntity group = groupRepository.findByGroupCode(GroupId);
        List<GetNotice> noticeList = noticeRepository.findByGroupCode(group);

        return noticeList;
    }

    @Override
    public Optional<NoticeEntity> getNoticeDetail(Integer NoticeId) {
        Optional<NoticeEntity> notice = noticeRepository.findByNoticeId(NoticeId);

        if (notice == null) return null;
        else return notice;
    }

    @Override
    public Optional<GetRecentNotice> getRecentNoticeOne(String groupId) {
        GroupEntity group = groupRepository.findByGroupCode(groupId);
        Optional<GetRecentNotice> notice = noticeRepository.findTopByGroupCodeOrderByCreateAtDesc(group);

        if (notice == null) return null;
        else return notice;
    }
}
