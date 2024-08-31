package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.notice.CreateNoticeDTO;
import com.narsha.moongge.base.dto.notice.NoticeDTO;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.NoticeEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.UserType;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.NoticeRepository;
import com.narsha.moongge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private NoticeService noticeService;

    @Test
    void 공지_작성하기() {

        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);
        CreateNoticeDTO createNoticeDTO = buildCreateNoticeDTO(user);

        // when
        NoticeDTO noticeDTO = noticeService.createNotice(user.getUserId(), createNoticeDTO);

        // then
        Optional<NoticeEntity> savedNotice = noticeRepository.findByNoticeId(noticeDTO.getNoticeId());
        assertTrue(savedNotice.isPresent(), "공지가 저장되어 있어야 합니다.");

        NoticeEntity notice = savedNotice.get();
        assertEquals(createNoticeDTO.getNoticeTitle(), notice.getNoticeTitle(), "제목이 일치해야 합니다.");
        assertEquals(createNoticeDTO.getNoticeContent(), notice.getNoticeContent(), "내용이 일치해야 합니다.");
        assertEquals(user.getUserId(), notice.getUser().getUserId(), "작성자가 일치해야 합니다.");
        assertEquals(group.getGroupCode(), notice.getGroup().getGroupCode(), "그룹 코드가 일치해야 합니다.");
        assertNotNull(notice.getCreateAt(), "생성일시가 존재해야 합니다.");
    }

    @Test
    void 공지_목록_불러오기() {

        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);
        CreateNoticeDTO createNoticeDTO = buildCreateNoticeDTO(user);

        NoticeDTO noticeDTO = noticeService.createNotice(user.getUserId(), createNoticeDTO);

        // when
        List<NoticeDTO> noticeList = noticeService.getNoticeList(user.getUserId());

        // then
        assertFalse(noticeList.isEmpty(), "공지 목록이 비어 있으면 안 됩니다.");
        assertEquals(1, noticeList.size(), "공지 목록에 하나의 공지가 있어야 합니다.");

        NoticeDTO retrievedNotice = noticeList.get(0);
        assertEquals(noticeDTO.getNoticeId(), retrievedNotice.getNoticeId(), "공지 ID가 일치해야 합니다.");
        assertEquals(createNoticeDTO.getNoticeTitle(), retrievedNotice.getNoticeTitle(), "공지 제목이 일치해야 합니다.");
        assertEquals(createNoticeDTO.getNoticeContent(), retrievedNotice.getNoticeContent(), "공지 내용이 일치해야 합니다.");
        assertEquals(user.getUserId(), retrievedNotice.getWriter(), "작성자가 일치해야 합니다.");
    }

    @Test
    void 최신_공지_목록_불러오기() {

        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);
        CreateNoticeDTO createNoticeDTO1 = buildCreateNoticeDTO(user);
        noticeService.createNotice(user.getUserId(), createNoticeDTO1);

        CreateNoticeDTO createNoticeDTO2 = buildCreateNoticeDTO(user);
        NoticeDTO recentNoticeDTO = noticeService.createNotice(user.getUserId(), createNoticeDTO2);

        // when
        NoticeDTO noticeDTO = noticeService.getRecentNoticeOne(user.getUserId());

        Optional<NoticeEntity> savedNotice = noticeRepository.findByNoticeId(noticeDTO.getNoticeId());
        assertTrue(savedNotice.isPresent());

        NoticeEntity notice = savedNotice.get();

        // then
        assertEquals(recentNoticeDTO.getNoticeTitle(), notice.getNoticeTitle(), "최신 공지 제목이 일치해야 합니다.");
        assertEquals(recentNoticeDTO.getNoticeContent(), notice.getNoticeContent(), "최신 공지 내용이 일치해야 합니다.");
        assertEquals(user.getUserId(), notice.getUser().getUserId(), "최신 공지 작성자가 일치해야 합니다.");
        assertEquals(group.getGroupCode(), notice.getGroup().getGroupCode(), "최신 공지 그룹 코드가 일치해야 합니다.");
    }

    @Test
    void 공지_상세_불러오기() {
        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);
        CreateNoticeDTO createNoticeDTO = buildCreateNoticeDTO(user);
        NoticeDTO noticeDTO = noticeService.createNotice(user.getUserId(), createNoticeDTO);

        // when
        NoticeDTO findNoticeDTO = noticeService.getNoticeDetail(user.getUserId(), noticeDTO.getNoticeId());

        // then
        assertEquals(createNoticeDTO.getNoticeTitle(), findNoticeDTO.getNoticeTitle(), "공지 제목이 일치해야 합니다.");
        assertEquals(createNoticeDTO.getNoticeContent(), findNoticeDTO.getNoticeContent(), "공지 내용이 일치해야 합니다.");
        assertEquals(user.getUserId(), findNoticeDTO.getWriter(), "공지 작성자가 일치해야 합니다.");

    }

    private CreateNoticeDTO buildCreateNoticeDTO(UserEntity user) {
        return CreateNoticeDTO.builder()
                .noticeTitle("title")
                .noticeContent("content")
                .writer(user.getUserId())
                .build();
    }

    private GroupEntity createGroup(UserEntity user) {
        GroupEntity group = GroupEntity.builder()
                .groupCode("groupCode")
                .groupName("groupName")
                .school("school")
                .grade(3)
                .groupClass(5)
                .build();

        GroupEntity savedGroup = groupRepository.save(group);

        user.updateGroup(savedGroup);

        return savedGroup;
    }

    private UserEntity createUser() {
        UserEntity user = UserEntity.builder()
                .userId("userId")
                .userType(UserType.TEACHER)
                .password("password")
                .userName("name")
                .build();

        return userRepository.save(user);
    }
}