package com.narsha.moongge.repository;

import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.NoticeEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.notice.CreateNoticeDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
import com.narsha.moongge.service.GroupService;
import com.narsha.moongge.service.NoticeService;
import com.narsha.moongge.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeRepositoryTest {

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
    void 최신_공지_불러오기() {

        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);
        NoticeEntity firstNotice = createNotice(user, group, "first title", "first content");
        NoticeEntity secondNotice = createNotice(user, group, "second title", "second content");

        noticeRepository.save(firstNotice);
        noticeRepository.save(secondNotice);

        // when
        Optional<NoticeEntity> latestNoticeOpt = noticeRepository.findTopByGroupOrderByCreateAtDesc(group);

        // then
        assertTrue(latestNoticeOpt.isPresent(), "최신 공지가 존재해야 합니다.");
        NoticeEntity latestNotice = latestNoticeOpt.get();

        assertEquals(secondNotice.getNoticeTitle(), latestNotice.getNoticeTitle(), "최신 공지 제목이 일치해야 합니다.");
        assertEquals(secondNotice.getNoticeContent(), latestNotice.getNoticeContent(), "최신 공지 내용이 일치해야 합니다.");
        assertEquals(user.getUserId(), latestNotice.getUser().getUserId(), "최신 공지 작성자가 일치해야 합니다.");
        assertEquals(group.getGroupCode(), latestNotice.getGroup().getGroupCode(), "최신 공지 그룹 코드가 일치해야 합니다.");
    }

    private CreateNoticeDTO buildCreateNoticeDTO(UserEntity user, GroupEntity group, String title, String content) {
        return CreateNoticeDTO.builder()
                .groupCode(group.getGroupCode())
                .noticeTitle(title)
                .noticeContent(content)
                .writer(user.getUserId())
                .build();
    }

    private NoticeEntity createNotice(UserEntity user, GroupEntity group, String title, String content) {
        return NoticeEntity.builder()
                .user(user)
                .group(group)
                .noticeTitle(title)
                .noticeContent(content)
                .build();
    }

    private GroupEntity createGroup(UserEntity user) {
        CreateGroupDTO createGroupDTO = CreateGroupDTO.builder()
                .groupName("groupName")
                .school("school")
                .grade(3)
                .groupClass(5)
                .userId(user.getUserId())
                .build();

        groupService.createGroup(createGroupDTO);

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
