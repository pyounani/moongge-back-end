package com.narsha.moongge.repository;

import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.NoticeEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.UserType;
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
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private NoticeRepository noticeRepository;

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

    private NoticeEntity createNotice(UserEntity user, GroupEntity group, String title, String content) {
        return NoticeEntity.builder()
                .user(user)
                .group(group)
                .noticeTitle(title)
                .noticeContent(content)
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
