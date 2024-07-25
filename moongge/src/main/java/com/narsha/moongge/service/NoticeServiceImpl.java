package com.narsha.moongge.service;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.notice.CreateNoticeDTO;
import com.narsha.moongge.base.dto.notice.NoticeDTO;
import com.narsha.moongge.base.exception.*;
import com.narsha.moongge.entity.NoticeEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.NoticeRepository;
import com.narsha.moongge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;


    /**
     * 공지 작성하기
     */
    @Override
    @Transactional
    public NoticeDTO createNotice(String groupCode, CreateNoticeDTO createNoticeDTO) {

        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        UserEntity user = userRepository.findByUserId(createNoticeDTO.getWriter())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 학생 유형 사용자 검증
        if ("student".equals(user.getUserType())) {
            throw new StudentNoticeCreationException(ErrorCode.STUDENT_NOT_ALLOWED);
        }

        NoticeEntity notice = NoticeEntity.builder()
                .group(group)
                .noticeTitle(createNoticeDTO.getNoticeTitle())
                .noticeContent(createNoticeDTO.getNoticeContent())
                .user(user)
                .build();

        NoticeEntity savedNotice = noticeRepository.save(notice);

        return NoticeDTO.mapToNoticeDTO(savedNotice);
    }

    /**
     * 공지 목록 불러오기
     */
    @Override
    public List<NoticeDTO> getNoticeList(String groupId) {
        GroupEntity group = groupRepository.findByGroupCode(groupId)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        List<NoticeEntity> noticeList = noticeRepository.findByGroup(group);

        return noticeList.stream()
                .map(NoticeDTO::mapToNoticeDTO)
                .collect(Collectors.toList());
    }

    /**
     * 공지 상세 불러오기
     */
    @Override
    public NoticeDTO getNoticeDetail(String groupCode, Integer noticeId) {
        NoticeEntity notice = noticeRepository.findByNoticeId(noticeId)
                .orElseThrow(() -> new NoticeNotFoundException(ErrorCode.NOTICE_NOT_FOUND));

        if (!groupCode.equals(notice.getGroup().getGroupCode())) {
            throw new GroupMismatchException(ErrorCode.GROUP_MISMATCH);
        }

        return NoticeDTO.mapToNoticeDTO(notice);
    }

    /**
     * 최근에 올린 공지 한 개 불러오기
     */
    @Override
    public NoticeDTO getRecentNoticeOne(String groupCode) {
        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        NoticeEntity notice = noticeRepository.findTopByGroupOrderByCreateAtDesc(group)
                .orElseThrow(() -> new NoticeNotFoundException(ErrorCode.NOTICE_NOT_FOUND));

        return NoticeDTO.mapToNoticeDTO(notice);
    }
}
