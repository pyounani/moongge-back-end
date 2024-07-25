package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.notice.CreateNoticeDTO;
import com.narsha.moongge.base.dto.notice.NoticeDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.NoticeEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.NoticeRepository;
import com.narsha.moongge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeServiceImplTest {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NoticeService noticeService;

    @Test
    void 공지_작성하기() {
        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);
        CreateNoticeDTO createNoticeDTO = buildCreateNoticeDTO(user, group);

        // when
        NoticeDTO noticeDTO = noticeService.createNotice(group.getGroupCode(), createNoticeDTO);

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

    private CreateNoticeDTO buildCreateNoticeDTO(UserEntity user, GroupEntity group) {
        return CreateNoticeDTO.builder()
                .groupCode(group.getGroupCode())
                .noticeTitle("title")
                .noticeContent("content")
                .writer(user.getUserId())
                .build();
    }

    private GroupEntity createGroup(UserEntity user) {
        CreateGroupDTO createGroupDTO = CreateGroupDTO.builder()
                .groupName("groupName")
                .school("school")
                .grade(3)
                .group_class(5)
                .userId(user.getUserId())
                .build();

        String userId = groupService.createGroup(createGroupDTO);

        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(user.getGroup().getGroupCode());
        assertTrue(savedGroup.isPresent());
        return savedGroup.get();
    }

    private UserEntity createUser() {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .userId("userId")
                .userType("teacher")
                .password("password")
                .name("name")
                .build();

        userService.register(userRegisterDTO);

        Optional<UserEntity> savedUser = userRepository.findByUserId("userId");
        assertTrue(savedUser.isPresent(), "유저가 저장되어 있어야 합니다.");
        return savedUser.get();
    }
}