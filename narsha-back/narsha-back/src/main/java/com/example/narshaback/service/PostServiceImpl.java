package com.example.narshaback.service;

import com.example.narshaback.dto.post.UploadPostDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.projection.post.GetPostDetail;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.projection.post.GetUserPost;
import com.example.narshaback.repository.PostRepository;
import com.example.narshaback.repository.UserGroupRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;

    private final UserGroupRepository userGroupRepository;

    @Override
    public Integer uploadPost(UploadPostDTO uploadPostDTO) {
        // 해당 유저-그룹 찾기
        Optional<User_Group> user_group = userGroupRepository.findByUserGroupId(uploadPostDTO.getUserGroupId());

        PostEntity post = PostEntity.builder()
                .content(uploadPostDTO.getContent())
                .imageArray(uploadPostDTO.getImageArray())
                .userGroupId(user_group.get())
                .build();
        PostEntity uploadPost = postRepository.save(post);
        if (uploadPost == null) return null;
        return uploadPost.getPostId();
    }

    @Override
    public List<GetUserPost> getUserPost(Integer userGroupId) {
        Optional<User_Group> user_group = userGroupRepository.findByUserGroupId(userGroupId);
        List<GetUserPost> postList = postRepository.findByUserGroupId(user_group.get());

        return postList;
    }

    @Override
    public GetPostDetail getPostDetail(Integer postId, Integer userGroupCode) {
        Optional<User_Group> user_group = userGroupRepository.findById(userGroupCode);
        Optional<PostEntity> post = postRepository.findByPostIdAndUserGroupId(postId, user_group.get());
        System.out.println(post);
        if(post.isPresent()) {

            // repository에서 projection으로 반환받아서 못 가져오기에 service 내부에 mapping 코드 필요...
            GetPostDetail res = new GetPostDetail() {
                @Override
                public Integer getId() {
                    return post.get().getPostId();
                }

                @Override
                public String getContent() {
                    return post.get().getContent();
                }

                @Override
                public String getImageArray() {
                    return post.get().getImageArray();
                }

                @Override
                public LocalDateTime getCreateAt() {
                    return post.get().getCreateAt();
                }

                @Override
                public UserEntity getWriter() {
                    return post.get().getUserGroupId().getUserId();
                }
            };

            return res;
        } else {
            throw new EntityNotFoundException(String.format("포스트 아이디 %d로 조회되지 않았습니다", postId));
        }
    }
}
