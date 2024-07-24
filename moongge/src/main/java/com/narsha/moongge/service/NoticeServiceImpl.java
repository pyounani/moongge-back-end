package com.narsha.moongge.service;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.notice.CreateNoticeDTO;
import com.narsha.moongge.base.exception.GroupCodeNotFoundException;
import com.narsha.moongge.base.exception.NoticeNotFoundException;
import com.narsha.moongge.base.exception.UserNotFoundException;
import com.narsha.moongge.base.projection.notice.GetNotice;
import com.narsha.moongge.base.projection.notice.GetRecentNotice;
import com.narsha.moongge.entity.NoticeEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.NoticeRepository;
import com.narsha.moongge.repository.UserRepository;
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
                    .group(group.get())
                    .noticeTitle(createNoticeDTO.getNoticeTitle())
                    .noticeContent(createNoticeDTO.getNoticeContent())
                    .user(user.get())
                    .build();

            noticeRepository.save(notice);

            return true;
        } else return false;

    }

    //공지 목록
    @Override
    public List<GetNotice> getNoticeList(String GroupId) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(GroupId);
        if(!group.isPresent())
            throw new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);

        List<GetNotice> noticeList = noticeRepository.findByGroup(group.get());

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

        Optional<GetRecentNotice> notice = noticeRepository.findTopByGroupOrderByCreateAtDesc(group.get());

        if (notice == null) return null;
        else return notice;
    }
}
