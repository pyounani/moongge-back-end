package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.like.CreateLikeDTO;
import com.narsha.moongge.base.dto.like.LikeDTO;
import com.narsha.moongge.base.exception.LikeAlreadyExistsException;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.LikeEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.LikeRepository;
import com.narsha.moongge.repository.PostRepository;
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
class LikeServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeService likeService;
    @Autowired
    private LikeRepository likeRepository;

    @Test
    void 좋아요_생성하기() {

        // given
        UserEntity user = createUser("userId");
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateLikeDTO createLikeDTO = buildCreateLikeDTO(user, group, post);

        // when
        Integer savedLikeId = likeService.createLike(group.getGroupCode(), post.getPostId(), createLikeDTO);

        // then
        Optional<LikeEntity> findLike = likeRepository.findById(savedLikeId);
        assertTrue(findLike.isPresent());

        LikeEntity like = findLike.get();

        assertEquals(savedLikeId, like.getLikeId());
        assertEquals(createLikeDTO.getGroupCode(), like.getGroup().getGroupCode());
        assertEquals(createLikeDTO.getPostId(), like.getPost().getPostId());
        assertEquals(createLikeDTO.getUserId(), like.getUser().getUserId());
    }

    @Test
    void 이미_좋아요_생성된_유저_예외처리() {

        // given
        UserEntity user = createUser("userId");
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateLikeDTO createLikeDTO = buildCreateLikeDTO(user, group, post);
        likeService.createLike(group.getGroupCode(), post.getPostId(), createLikeDTO);

        // then
        assertThrows(LikeAlreadyExistsException.class, () -> {
            likeService.createLike(group.getGroupCode(), post.getPostId(), createLikeDTO);
        });
    }

    @Test
    void 좋아요_목록_가져오기() {

        // given
        UserEntity user1 = createUser("userId1");
        GroupEntity group = createGroup(user1);

        UserEntity user2 = createUser("userId2");
        joinGroup(user2, group);

        PostEntity post = createPost(user1, group);

        CreateLikeDTO createLikeDTOByUser1 = buildCreateLikeDTO(user1, group, post);
        Integer likeId1 = likeService.createLike(group.getGroupCode(), post.getPostId(), createLikeDTOByUser1);

        CreateLikeDTO createLikeDTObyUser2 = buildCreateLikeDTO(user2, group, post);
        Integer likeId2 = likeService.createLike(group.getGroupCode(), post.getPostId(), createLikeDTObyUser2);

        // when
        List<LikeDTO> likeList = likeService.getLikeList(group.getGroupCode(), post.getPostId());

        // then
        assertNotNull(likeList, "좋아요 목록은 null이 아니어야 합니다.");
        assertEquals(2, likeList.size(), "좋아요 목록의 크기는 2이어야 합니다.");

        assertLikeListContains(likeList, likeId1, user1, "좋아요 목록에 첫 번째 좋아요가 포함되어 있어야 합니다.");
        assertLikeListContains(likeList, likeId2, user2, "좋아요 목록에 두 번째 좋아요가 포함되어 있어야 합니다.");
    }

    private static void assertLikeListContains(List<LikeDTO> likeList, Integer likeId1, UserEntity user1, String message) {
        boolean containsLike1 = likeList.stream()
                .anyMatch(like -> like.getLikeId().equals(likeId1) &&
                        like.getWriter().equals(user1.getUserId()) &&
                        like.getUsername().equals(user1.getUserName()));
        assertTrue(containsLike1, message);
    }

    private CreateLikeDTO buildCreateLikeDTO(UserEntity user, GroupEntity group, PostEntity post) {
        return CreateLikeDTO.builder()
                .groupCode(group.getGroupCode())
                .postId(post.getPostId())
                .userId(user.getUserId())
                .build();
    }

    private void joinGroup(UserEntity user, GroupEntity group) {
        user.setGroup(group);
    }

    private PostEntity createPost(UserEntity user, GroupEntity group) {
        PostEntity post = PostEntity.builder()
                .user(user)
                .group(group)
                .content("content")
                .build();

        return postRepository.save(post);
    }

    private UserEntity createUser(String userId) {
        UserEntity user = UserEntity.builder()
                .userId(userId)
                .userType("teacher")
                .password("password")
                .userName("name")
                .build();

        return userRepository.save(user);
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

        user.setGroup(savedGroup);

        return savedGroup;
    }

}