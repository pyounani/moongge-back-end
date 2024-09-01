package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.like.CreateLikeDTO;
import com.narsha.moongge.base.dto.like.DeleteLikeDTO;
import com.narsha.moongge.base.dto.like.LikeDTO;
import com.narsha.moongge.base.exception.LikeAlreadyExistsException;
import com.narsha.moongge.entity.*;
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

    private static final int MIN_LIKES_REQUIRED = 10;

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
        Integer savedLikeId = likeService.createLike(user.getUserId(), post.getPostId(), createLikeDTO);

        // then
        Optional<LikeEntity> findLike = likeRepository.findById(savedLikeId);
        assertTrue(findLike.isPresent());

        LikeEntity like = findLike.get();

        assertEquals(savedLikeId, like.getLikeId());
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
        likeService.createLike(user.getUserId(), post.getPostId(), createLikeDTO);

        // then
        assertThrows(LikeAlreadyExistsException.class, () -> {
            likeService.createLike(user.getUserId(), post.getPostId(), createLikeDTO);
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
        Integer likeId1 = likeService.createLike(user1.getUserId(), post.getPostId(), createLikeDTOByUser1);

        CreateLikeDTO createLikeDTObyUser2 = buildCreateLikeDTO(user2, group, post);
        Integer likeId2 = likeService.createLike(user2.getUserId(), post.getPostId(), createLikeDTObyUser2);

        // when
        List<LikeDTO> likeList = likeService.getLikeList(group.getGroupCode(), post.getPostId());

        // then
        assertNotNull(likeList, "좋아요 목록은 null이 아니어야 합니다.");
        assertEquals(2, likeList.size(), "좋아요 목록의 크기는 2이어야 합니다.");

        assertLikeListContains(likeList, likeId1, user1, "좋아요 목록에 첫 번째 좋아요가 포함되어 있어야 합니다.");
        assertLikeListContains(likeList, likeId2, user2, "좋아요 목록에 두 번째 좋아요가 포함되어 있어야 합니다.");
    }

    @Test
    void 좋아요_취소() {
        // given
        UserEntity user = createUser("userId");
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateLikeDTO createLikeDTO = buildCreateLikeDTO(user, group, post);
        Integer savedLikeId = likeService.createLike(user.getUserId(), post.getPostId(), createLikeDTO);

        DeleteLikeDTO deleteLikeDTO = buildDeleteLikeDTO(user, group, post);
        likeService.deleteLike(group.getGroupCode(), post.getPostId(), deleteLikeDTO);

        Optional<LikeEntity> findLike = likeRepository.findById(savedLikeId);
        assertTrue(findLike.isEmpty());
    }

    @Test
    void 좋아요_여부_가져오기() {
        // given
        UserEntity user = createUser("userId");
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateLikeDTO createLikeDTO = buildCreateLikeDTO(user, group, post);
        likeService.createLike(user.getUserId(), post.getPostId(), createLikeDTO);

        // when
        Boolean checkLikePost = likeService.checkLikePost(user.getUserId(), group.getGroupCode(), post.getPostId());

        // then
        assertTrue(checkLikePost);
    }

    @Test
    void 좋아요_누르지_않은_경우_가져오기() {

        // given
        UserEntity user = createUser("userId");
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateLikeDTO createLikeDTO = buildCreateLikeDTO(user, group, post);
        likeService.createLike(user.getUserId(), post.getPostId(), createLikeDTO);

        DeleteLikeDTO deleteLikeDTO = buildDeleteLikeDTO(user, group, post);
        likeService.deleteLike(group.getGroupCode(), post.getPostId(), deleteLikeDTO);

        // when
        Boolean checkLikePost = likeService.checkLikePost(user.getUserId(), group.getGroupCode(), post.getPostId());

        // then
        assertFalse(checkLikePost);
    }

    @Test
    void 좋아요_갯수_가져오기() {
        // given
        UserEntity user1 = createUser("userId1");
        GroupEntity group = createGroup(user1);

        UserEntity user2 = createUser("userId2");
        joinGroup(user2, group);

        PostEntity post = createPost(user1, group);

        CreateLikeDTO createLikeDTOByUser1 = buildCreateLikeDTO(user1, group, post);
        likeService.createLike(user1.getUserId(), post.getPostId(), createLikeDTOByUser1);

        CreateLikeDTO createLikeDTObyUser2 = buildCreateLikeDTO(user2, group, post);
        likeService.createLike(user2.getUserId(), post.getPostId(), createLikeDTObyUser2);

        // when
        Long countLike = likeService.countLike(group.getGroupCode(), post.getPostId());

        // then
        assertEquals(2, countLike);
    }

    @Test
    void 유저_쓴_포스트_좋아요_10개_넘는_여부() {

        // given
        UserEntity user = createUser("userId");
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        // 여러 유저 생성 및 post에 좋아요 누르기
        createTenLikeByManyUser(group, post);

        // when
        Boolean receiveTenLikes = likeService.receiveTenLikes(group.getGroupCode(), user.getUserId());

        // then
        assertTrue(receiveTenLikes);
    }

    @Test
    void 유저_쓴_포스트_좋아요_10개_넘지_못하는_여부() {

        // given
        UserEntity user = createUser("userId");
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateLikeDTO createLikeDTO = buildCreateLikeDTO(user, group, post);
        likeService.createLike(user.getUserId(), post.getPostId(), createLikeDTO);

        // when
        Boolean receiveTenLikes = likeService.receiveTenLikes(group.getGroupCode(), user.getUserId());

        // then
        assertFalse(receiveTenLikes);
    }

    @Test
    void 유저가_좋아요_10개_눌렀는지_여부() {
        // given
        UserEntity giveLikeUser = createUser("giveLikeUser");
        GroupEntity group = createGroup(giveLikeUser);

        // 여러 유저 생성 및 포스트 생성 및 유저(giveLikeUser)가 좋아요 누르기
        createTenLikeByOneUser(giveLikeUser, group);

        // when
        Boolean giveTenLikes = likeService.giveTenLikes(group.getGroupCode(), giveLikeUser.getUserId());

        // then
        assertTrue(giveTenLikes);
    }

    @Test
    void 유저가_좋아요_10개_누르지_못한_여부() {
        // given
        UserEntity user = createUser("userId");
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateLikeDTO createLikeDTO = buildCreateLikeDTO(user, group, post);
        likeService.createLike(user.getUserId(), post.getPostId(), createLikeDTO);

        Boolean giveTenLikes = likeService.giveTenLikes(group.getGroupCode(), user.getUserId());

        assertFalse(giveTenLikes);
    }

    private void createTenLikeByOneUser(UserEntity giveLikeUser, GroupEntity group) {
        for (int i = 1; i <= MIN_LIKES_REQUIRED; i++) {
            createPostAndLike(giveLikeUser, group, i);
        }
    }

    private void createPostAndLike(UserEntity giveLikeUser, GroupEntity group, int i) {
        UserEntity user = createUser("user" + i);
        joinGroup(user, group);

        PostEntity post1 = createPost(user, group);

        CreateLikeDTO createLikeDTO1 = buildCreateLikeDTO(giveLikeUser, group, post1);
        likeService.createLike(giveLikeUser.getUserId(), post1.getPostId(), createLikeDTO1);
    }

    private void createTenLikeByManyUser(GroupEntity group, PostEntity post) {
        for (int i = 1; i <= MIN_LIKES_REQUIRED; i++) {
            createUserAndLike("giveLikeUser" + i, group, post);
        }
    }

    private void createUserAndLike(String userId, GroupEntity group, PostEntity post) {
        UserEntity user = createUser(userId);
        joinGroup(user, group);
        CreateLikeDTO createLikeDTObyUser2 = buildCreateLikeDTO(user, group, post);
        likeService.createLike(userId, post.getPostId(), createLikeDTObyUser2);
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
                .postId(post.getPostId())
                .userId(user.getUserId())
                .build();
    }

    private DeleteLikeDTO buildDeleteLikeDTO(UserEntity user, GroupEntity group, PostEntity post) {
        return DeleteLikeDTO.builder()
                .groupCode(group.getGroupCode())
                .postId(post.getPostId())
                .userId(user.getUserId())
                .build();
    }

    private void joinGroup(UserEntity user, GroupEntity group) {
        user.updateGroup(group);
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
                .userType(UserType.TEACHER)
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

        user.updateGroup(savedGroup);

        return savedGroup;
    }

}