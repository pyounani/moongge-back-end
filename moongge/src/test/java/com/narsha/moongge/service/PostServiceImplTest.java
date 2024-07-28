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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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

    private List<String> uploadedFileUrls;

    @BeforeEach
    void setUp() {
        uploadedFileUrls = new ArrayList<>();
    }

    @AfterEach
    void deleteFileInS3() {
        if (uploadedFileUrls != null) {
            for (String uploadedFileUrl : uploadedFileUrls) {
                amazonS3Service.deleteS3(uploadedFileUrl);
            }
        }
    }

    @Test
    void 포스트_업로드() {

        // given
        UserEntity user = createUser("user");
        GroupEntity group = createGroup(user);

        MultipartFile[] multipartFiles = createMultipartFile();
        UploadPostDTO uploadPostDTO = buildUploadPostDTO(user, group);

        // when
        PostDTO postDTO = postService.uploadPost(uploadPostDTO.getGroupCode(), multipartFiles, uploadPostDTO);

        // then
        Optional<PostEntity> savedPost = postRepository.findByPostId(postDTO.getPostId());
        assertTrue(savedPost.isPresent());

        PostEntity post = savedPost.get();
        assertEquals(uploadPostDTO.getContent(), post.getContent(), "포스트 내용이 일치해야 합니다.");
        assertTrue(post.getImageArray().contains("testImage.jpg"), "이미지 URL이 포스트에 포함되어야 합니다.");
        assertEquals(user.getUserId(), post.getUser().getUserId(), "포스트 작성자가 일치해야 합니다.");
        assertEquals(group.getGroupCode(), post.getGroup().getGroupCode(), "포스트 그룹 코드가 일치해야 합니다.");

        uploadedFileUrls.add(postDTO.getImageArray());
    }

    @Test
    void 포스트_상세_불러오기() {

        //given
        UserEntity user = createUser("user");
        GroupEntity group = createGroup(user);

        MultipartFile[] multipartFiles = createMultipartFile();
        UploadPostDTO uploadPostDTO = buildUploadPostDTO(user, group);

        PostDTO savedPostDTO = postService.uploadPost(uploadPostDTO.getGroupCode(), multipartFiles, uploadPostDTO);

        // when
        PostDTO findPostDTO = postService.getPostDetail(savedPostDTO.getGroupCode(), savedPostDTO.getPostId());

        //then
        assertEquals(uploadPostDTO.getContent(), findPostDTO.getContent(), "포스트 내용이 일치해야 합니다.");
        assertTrue(findPostDTO.getImageArray().contains("testImage.jpg"), "이미지 URL이 포스트에 포함되어야 합니다.");
        assertEquals(user.getUserId(), findPostDTO.getWriter(), "포스트 작성자가 일치해야 합니다.");
        assertEquals(group.getGroupCode(), findPostDTO.getGroupCode(), "포스트 그룹 코드가 일치해야 합니다.");

        uploadedFileUrls.add(savedPostDTO.getImageArray());
    }

    @Test
    void 유저가_올린_포스트_목록() {
        // given
        UserEntity user = createUser("user");
        GroupEntity group = createGroup(user);

        MultipartFile[] multipartFiles = createMultipartFile();
        UploadPostDTO uploadPostDTO = buildUploadPostDTO(user, group);

        PostDTO savedPostDTO1 = postService.uploadPost(uploadPostDTO.getGroupCode(), multipartFiles, uploadPostDTO);
        PostDTO savedPostDTO2 = postService.uploadPost(uploadPostDTO.getGroupCode(), multipartFiles, uploadPostDTO);

        // when
        List<PostDTO> findPostDTOList = postService.getUserPost(savedPostDTO1.getWriter());

        // then
        assertFalse(findPostDTOList.isEmpty(), "포스트 목록이 비어있으면 안됩니다.");
        assertTrue(findPostDTOList.stream().anyMatch(postDTO -> postDTO.getPostId().equals(savedPostDTO1.getPostId())), "첫 번째 저장된 포스트가 목록에 포함되어야 합니다.");
        assertTrue(findPostDTOList.stream().anyMatch(postDTO -> postDTO.getPostId().equals(savedPostDTO2.getPostId())), "두 번째 저장된 포스트가 목록에 포함되어야 합니다.");

        uploadedFileUrls.add(savedPostDTO1.getImageArray());
        uploadedFileUrls.add(savedPostDTO2.getImageArray());
    }

    @Test
    void 유저가_올리지_않은_목록_포함시키지_않기() {
        // given
        UserEntity user1 = createUser("user1");
        UserEntity user2 = createUser("user2");
        GroupEntity group1 = createGroup(user1);
        GroupEntity group2 = createGroup(user2);

        MultipartFile[] multipartFiles = createMultipartFile();
        UploadPostDTO uploadPostDTO1 = buildUploadPostDTO(user1, group1);

        UploadPostDTO uploadPostDTO2 = buildUploadPostDTO(user2, group2);

        PostDTO savedPostDTO1 = postService.uploadPost(uploadPostDTO1.getGroupCode(), multipartFiles, uploadPostDTO1);
        PostDTO savedPostDTO2 = postService.uploadPost(uploadPostDTO2.getGroupCode(), multipartFiles, uploadPostDTO2);

        // when
        List<PostDTO> findPostDTOList = postService.getUserPost(savedPostDTO1.getWriter());

        // then
        assertFalse(findPostDTOList.isEmpty(), "포스트 목록이 비어있으면 안됩니다.");
        assertTrue(findPostDTOList.stream().anyMatch(postDTO -> postDTO.getPostId().equals(savedPostDTO1.getPostId())), "첫 번째 저장된 포스트가 목록에 포함되어야 합니다.");
        assertFalse(findPostDTOList.stream().anyMatch(postDTO -> postDTO.getPostId().equals(savedPostDTO2.getPostId())), "두 번째 저장된 포스트가 목록에 포함되지 않아야 합니다.");

        uploadedFileUrls.add(savedPostDTO1.getImageArray());
        uploadedFileUrls.add(savedPostDTO2.getImageArray());
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

    private UserEntity createUser(String userId) {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .userId(userId)
                .userType("teacher")
                .password("password")
                .name("name")
                .build();

        userService.register(userRegisterDTO);

        Optional<UserEntity> savedUser = userRepository.findByUserId(userId);
        assertTrue(savedUser.isPresent(), "유저가 저장되어 있어야 합니다.");
        return savedUser.get();
    }
}