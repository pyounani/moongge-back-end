package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.post.PostDTO;
import com.narsha.moongge.base.dto.post.UploadPostDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.PostRepository;
import com.narsha.moongge.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceImplTest {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AmazonS3Service amazonS3Service;

    private String uploadedFileUrl;

    @AfterEach
    void deleteFileInS3() {
        if (uploadedFileUrl != null) {
            amazonS3Service.deleteS3(uploadedFileUrl);
        }
    }

    @Test
    void 포스트_엄로드() {

        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);

        MultipartFile[] multipartFiles = createMultipartFile();
        UploadPostDTO uploadPostDTO = buildUploadPostDTO(user, group);

        // when
        PostDTO postDTO = postService.uploadPost(multipartFiles, uploadPostDTO);

        // then
        Optional<PostEntity> savedPost = postRepository.findByPostId(postDTO.getPostId());
        assertTrue(savedPost.isPresent());

        PostEntity post = savedPost.get();
        assertEquals(uploadPostDTO.getContent(), post.getContent(), "포스트 내용이 일치해야 합니다.");
        assertTrue(post.getImageArray().contains("testImage.jpg"), "이미지 URL이 포스트에 포함되어야 합니다.");
        assertEquals(user.getUserId(), post.getUser().getUserId(), "포스트 작성자가 일치해야 합니다.");
        assertEquals(group.getGroupCode(), post.getGroupCode().getGroupCode(), "포스트 그룹 코드가 일치해야 합니다.");

        uploadedFileUrl = postDTO.getImageArray();
    }

    private MultipartFile[] createMultipartFile() {
        MultipartFile[] multipartFiles = new MultipartFile[]{
                new MockMultipartFile("file", "testImage.jpg", "image/jpeg", "test image content".getBytes())
        };
        return multipartFiles;
    }

    private UploadPostDTO buildUploadPostDTO(UserEntity user, GroupEntity group) {
        return UploadPostDTO.builder()
                .groupCode(group.getGroupCode())
                .writer(user.getUserId())
                .content("content")
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