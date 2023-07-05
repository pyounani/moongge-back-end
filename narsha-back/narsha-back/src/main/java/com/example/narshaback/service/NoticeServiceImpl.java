package com.example.narshaback.service;

import com.example.narshaback.base.dto.notice.CreateNoticeDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.NoticeEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.base.projection.notice.GetNotice;
import com.example.narshaback.base.projection.notice.GetRecentNotice;
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

        Optional<GroupEntity> group = groupRepository.findByGroupCode(createNoticeDTO.getGroupCode());
        Optional<UserEntity> user = userRepository.findByUserId(createNoticeDTO.getWriter());

        if (group.isPresent()){
            NoticeEntity notice = NoticeEntity.builder()
                    .groupCode(group.get())
                    .noticeTitle(createNoticeDTO.getNoticeTitle())
                    .noticeContent(createNoticeDTO.getNoticeContent())
                    .writer(user.get())
                    .build();
            noticeRepository.save(notice).getNoticeId();
            return true;
        } else return false;
    }

    @Override
    public List<GetNotice> getNoticeList(String GroupId) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(GroupId);
        List<GetNotice> noticeList = noticeRepository.findByGroupCode(group.get());

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
        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupId);
        Optional<GetRecentNotice> notice = noticeRepository.findTopByGroupCodeOrderByCreateAtDesc(group.get());

        if (notice == null) return null;
        else return notice;
    }
}
