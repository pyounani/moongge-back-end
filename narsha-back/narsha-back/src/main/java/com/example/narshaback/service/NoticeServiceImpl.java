package com.example.narshaback.service;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.dto.notice.CreateNoticeDTO;
import com.example.narshaback.base.exception.GroupCodeNotFoundException;
import com.example.narshaback.base.exception.NoticeNotFoundException;
import com.example.narshaback.base.exception.UserNotFoundException;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.NoticeEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.base.projection.notice.GetNotice;
import com.example.narshaback.base.projection.notice.GetRecentNotice;
import com.example.narshaback.event.NoticeCreatedEvent;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.NoticeRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private final GroupRepository groupRepository;

    private final UserRepository userRepository;

    private final ApplicationEventPublisher eventPublisher;

    //공지 작성
    @Override
    public Boolean createNotice(CreateNoticeDTO createNoticeDTO) {

        Optional<GroupEntity> group = groupRepository.findByGroupCode(createNoticeDTO.getGroupCode());
        if(!group.isPresent())
            throw new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);

        Optional<UserEntity> user = userRepository.findByUserId(createNoticeDTO.getWriter());
        if(!user.isPresent())
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);


        if (group.isPresent()){
            NoticeEntity notice = NoticeEntity.builder()
                    .groupCode(group.get())
                    .noticeTitle(createNoticeDTO.getNoticeTitle())
                    .noticeContent(createNoticeDTO.getNoticeContent())
                    .writer(user.get())
                    .build();

            noticeRepository.save(notice);

            NoticeCreatedEvent event = new NoticeCreatedEvent(this, notice);
            eventPublisher.publishEvent(event);

            return true;
        } else return false;

    }

    //공지 목록
    @Override
    public List<GetNotice> getNoticeList(String GroupId) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(GroupId);
        if(!group.isPresent())
            throw new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);

        List<GetNotice> noticeList = noticeRepository.findByGroupCode(group.get());

        return noticeList;
    }

    //공지 상세
    @Override
    public Optional<NoticeEntity> getNoticeDetail(Integer NoticeId) {
        Optional<NoticeEntity> notice = noticeRepository.findByNoticeId(NoticeId);
        if(!notice.isPresent())
            throw new NoticeNotFoundException(ErrorCode.NOTICE_NOT_FOUND);


        if (notice == null) return null;
        else return notice;
    }


    @Override
    public Optional<GetRecentNotice> getRecentNoticeOne(String groupId) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupId);
        if(!group.isPresent())
            throw new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);

        Optional<GetRecentNotice> notice = noticeRepository.findTopByGroupCodeOrderByCreateAtDesc(group.get());

        if (notice == null) return null;
        else return notice;
    }
}
