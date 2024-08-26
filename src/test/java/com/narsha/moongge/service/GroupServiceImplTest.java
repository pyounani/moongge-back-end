package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.GroupDTO;
import com.narsha.moongge.base.dto.group.JoinGroupDTO;
import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.base.dto.user.UserProfileDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.UserType;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    void 그룹_생성하기() {
        // given
        UserEntity user = createUserTeacher();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);

        // when
        String userId = groupService.createGroup(createGroupDTO);

        // then
        assertEquals(user.getUserId(), userId, "생성된 그룹의 유저 ID가 일치해야 합니다.");

        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(user.getGroup().getGroupCode());
        assertTrue(savedGroup.isPresent(), "그룹이 저장되어 있어야 합니다.");

        GroupEntity group = savedGroup.get();
        assertEquals(createGroupDTO.getGroupName(), group.getGroupName(), "그룹 이름이 일치해야 합니다.");
        assertEquals(createGroupDTO.getSchool(), group.getSchool(), "학교 이름이 일치해야 합니다.");
        assertEquals(createGroupDTO.getGrade(), group.getGrade(), "학년이 일치해야 합니다.");
        assertEquals(createGroupDTO.getGroupClass(), group.getGroupClass(), "반이 일치해야 합니다.");
    }

    @Test
    void 그룹_코드_불러오기() {

        // given
        UserEntity user = createUserTeacher();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        String userId = groupService.createGroup(createGroupDTO);

        assertEquals(user.getUserId(), userId);

        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(user.getGroup().getGroupCode());
        assertTrue(savedGroup.isPresent());

        // when
        String groupCode = groupService.getUserGroupCode(userId);

        // then
        assertEquals(savedGroup.get().getGroupCode(), groupCode);
    }

    @Test
    void 그룹_조인하기() {
        // 선생님 생성 및 그룹 생성
        UserEntity teacher = createUserTeacher();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(teacher);
        String userId = groupService.createGroup(createGroupDTO);

        String groupCode = groupService.getUserGroupCode(userId);

        // 학생 생성
        UserEntity student = createUserStudent();
        JoinGroupDTO joinGroupDTO = buildJoinGroupDTO(student, groupCode);

        // 그룹 조인
        GroupDTO groupDTO = groupService.joinGroup(joinGroupDTO);

        assertEquals(student.getUserId(), groupDTO.getUserId());
        assertEquals(student.getUserType(), groupDTO.getUserType());
        assertEquals(student.getUserName(), groupDTO.getUsername());

        assertEquals(student.getGroup().getGroupCode(), groupDTO.getGroupCode());
        assertEquals(student.getGroup().getGroupName(), groupDTO.getGroupName());
        assertEquals(student.getGroup().getSchool(), groupDTO.getSchool());
        assertEquals(student.getGroup().getGrade(), groupDTO.getGrade());
        assertEquals(student.getGroup().getGroupClass(), groupDTO.getGroupClass());
    }

    private JoinGroupDTO buildJoinGroupDTO(UserEntity student, String groupCode) {
        return JoinGroupDTO.builder()
                .userId(student.getUserId())
                .groupCode(groupCode)
                .build();
    }

    @Test
    void 그룹내_유저_목록_가져오기() {
        String teacherId = "teacher";
        String groupCode = createTeacherAndGroup(teacherId);

        createStudentAndJoinGroup("student1", groupCode);
        createStudentAndJoinGroup("student2", groupCode);
        createStudentAndJoinGroup("student3", groupCode);

        List<UserProfileDTO> studentList = groupService.getStudentList(groupCode, teacherId);

        assertEquals(3, studentList.size(), "학생 목록의 크기가 예상과 다릅니다.");
        // 각 학생이 목록에 포함되어 있는지 확인
        List<String> studentIds = List.of("student1", "student2", "student3");
        for (String studentId : studentIds) {
            boolean studentFound = studentList.stream()
                    .anyMatch(dto -> dto.getUserId().equals(studentId));
            assertTrue(studentFound, "학생 ID " + studentId + "가 목록에 없습니다.");
        }
    }

    private void createStudentAndJoinGroup(String userId, String groupCode) {
        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO(userId, "student");
        userService.register(userRegisterDTO, UserType.STUDENT);

        Optional<UserEntity> findUser = userRepository.findByUserId(userRegisterDTO.getUserId());
        assertTrue(findUser.isPresent());
        UserEntity user = findUser.get();

        JoinGroupDTO joinGroupDTO = buildJoinGroupDTO(user, groupCode);
        groupService.joinGroup(joinGroupDTO);
    }

    private String createTeacherAndGroup(String userId) {
        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO(userId, "teacher");
        userService.register(userRegisterDTO, UserType.TEACHER);

        Optional<UserEntity> findUser = userRepository.findByUserId(userRegisterDTO.getUserId());
        assertTrue(findUser.isPresent());
        UserEntity user = findUser.get();

        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        groupService.createGroup(createGroupDTO);

        return user.getGroup().getGroupCode();
    }

    @Test
    void 그룹_삭제하기() {
        // given
        UserEntity user = createUserTeacher();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        groupService.createGroup(createGroupDTO);

        String groupCode = user.getGroup().getGroupCode();

        // when
        groupService.deleteGroup(groupCode);

        // then
        Optional<UserEntity> updatedUserOpt = userRepository.findByUserId(user.getUserId());
        assertTrue(updatedUserOpt.isPresent(), "유저는 여전히 존재해야 합니다.");
        UserEntity updatedUser = updatedUserOpt.get();
        assertNull(updatedUser.getGroup(), "그룹 삭제 후 유저의 그룹은 null이어야 합니다.");

        Optional<GroupEntity> deletedGroupOpt = groupRepository.findByGroupCode(groupCode);
        assertFalse(deletedGroupOpt.isPresent(), "그룹은 삭제되어야 합니다.");
    }

    @Test
    void 그룹_시간_등록하기() {
        // given
        UserEntity user = createUserTeacher();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        groupService.createGroup(createGroupDTO);

        String groupCode = user.getGroup().getGroupCode();

        UpdateTimeDTO updateTimeDTO = UpdateTimeDTO.builder()
                .startTime(LocalTime.of(13, 0, 0))
                .endTime(LocalTime.of(15, 0, 0))
                .build();

        // when
        groupService.updateTime(groupCode, updateTimeDTO);

        // then
        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(groupCode);
        assertTrue(savedGroup.isPresent(), "그룹이 존재해야 합니다.");

        GroupEntity group = savedGroup.get();
        assertEquals(LocalTime.of(13, 0, 0), group.getStartTime(), "시작 시간이 일치해야 합니다.");
        assertEquals(LocalTime.of(15, 0, 0), group.getEndTime(), "종료 시간이 일치해야 합니다.");
    }

    @Test
    void 그룹_시간_조회하기() {
        // given
        UserEntity user = createUserTeacher();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        groupService.createGroup(createGroupDTO);

        String groupCode = user.getGroup().getGroupCode();

        UpdateTimeDTO updateTimeDTO = new UpdateTimeDTO();
        LocalTime startTime = LocalTime.of(13, 0, 0); // 13:00:00
        LocalTime endTime = LocalTime.of(15, 0, 0);   // 15:00:00

        updateTimeDTO.setStartTime(startTime);
        updateTimeDTO.setEndTime(endTime);

        groupService.updateTime(groupCode, updateTimeDTO);

        // when
        UpdateTimeDTO retrievedTimeDTO = groupService.getTime(groupCode);

        // then
        assertNotNull(retrievedTimeDTO, "그룹 시간 정보가 존재해야 합니다.");
        assertEquals(startTime, retrievedTimeDTO.getStartTime(), "시작 시간이 일치해야 합니다.");
        assertEquals(endTime, retrievedTimeDTO.getEndTime(), "종료 시간이 일치해야 합니다.");
        assertEquals(groupCode, retrievedTimeDTO.getGroupCode(), "그룹 코드가 일치해야 합니다.");
    }

    private UserRegisterDTO buildUserRegisterDTO(String userId, String userType) {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .userId(userId)
                .password("password")
                .userType(userType)
                .name("name")
                .build();
        return userRegisterDTO;
    }

    private CreateGroupDTO buildCreateGroupDTO(UserEntity user) {
        return CreateGroupDTO.builder()
                .groupName("groupName")
                .school("school")
                .grade(3)
                .groupClass(5)
                .userId(user.getUserId())
                .build();
    }

    private UserEntity createUserTeacher() {
        UserEntity user = UserEntity.builder()
                .userId("userId")
                .userType(UserType.TEACHER)
                .password("password")
                .userName("name")
                .build();

        return userRepository.save(user);
    }

    private UserEntity createUserStudent() {
        UserEntity user = UserEntity.builder()
                .userId("studentId")
                .userType(UserType.STUDENT)
                .password("password")
                .userName("name")
                .build();

        return userRepository.save(user);
    }
}
