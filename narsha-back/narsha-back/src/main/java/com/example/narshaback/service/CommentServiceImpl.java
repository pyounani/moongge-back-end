package com.example.narshaback.service;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.dto.comment.CreateCommentDTO;
import com.example.narshaback.base.exception.EmptyCommentContentException;
import com.example.narshaback.base.exception.GroupCodeNotFoundException;
import com.example.narshaback.base.exception.PostNotFoundException;
import com.example.narshaback.base.exception.UserNotFoundException;
import com.example.narshaback.entity.CommentEntity;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.base.projection.comment.GetComment;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.repository.CommentRepository;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.PostRepository;
import com.example.narshaback.repository.UserRepository;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final GroupRepository groupRepository;


    private final UserRepository userRepository;


    @Override
    public Integer createComment(CreateCommentDTO createCommentDTO) {

        Optional<UserEntity> user = userRepository.findByUserId(createCommentDTO.getUserId());
        if(!user.isPresent()) {
            throw new UserNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        Optional<PostEntity> post = postRepository.findByPostId(createCommentDTO.getPostId());

        // 게시물이 존재하지 않은 경우
        if (!post.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
        }

        // 댓글에 아무 내용이 없을 경우
        String content = createCommentDTO.getContent();
        if(content == null || content.trim().isEmpty()) {
            throw new EmptyCommentContentException(ErrorCode.EMPTY_COMMENT_CONTENT);
        }

        CommentEntity comment = CommentEntity.builder()
                .postId(post.get())
                .userId(user.get())
                .content(createCommentDTO.getContent())
                .build();

        return commentRepository.save(comment).getCommentId();
    }

    @Override
    public List<GetComment> getCommentList(Integer postId) {

        Optional<PostEntity> findPost = postRepository.findByPostId(postId);
        // 게시물이 존재하지 않은 경우
        if (!findPost.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
        }

        List<GetComment> commentList = commentRepository.findByPostId(findPost.get());

        return commentList;
    }

    @Override
    public Integer createAIComment(Integer postId) {

        //유저 검색
//        Optional<UserEntity> user = userRepository.findByUserId(createCommentDTO.getUserId());
//        if(!user.isPresent()) {
//            throw new UserNotFoundException(ErrorCode.USERID_NOT_FOUND);
//        }

        //포스트 검색
        Optional<PostEntity> post = postRepository.findByPostId(postId);

        // 게시물이 존재하지 않은 경우
        if (!post.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
        }

        Random rand = new Random();
        WebClient webClient = WebClient.create("http://localhost:8000");

        String post_content = "{\"post_content\": \"오늘은 첫 등교날 너무 설레.\"}";

        int teacher =1;
//        int teacher = rand.nextInt(1);
        if (teacher == 1) {

            Optional<UserEntity> user = userRepository.findByUserId("narsha1111");

            int num = rand.nextInt(2);
            if(num == 0){
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }
                String res = webClient.post()
                        .uri("/chat/teacher/content")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(post_content))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                System.out.println(res);

                //comment 저장
                CommentEntity comment = CommentEntity.builder()
                        .postId(post.get())
                        .userId(user.get())
                        .content(res)
                        .build();

                return commentRepository.save(comment).getCommentId();
            }
            else if(num == 1){
                try {
                    Thread.sleep(rand.nextInt(100)*10000); // 시간 늦추기
                } catch (InterruptedException e) {
                }
                // 이미지만 하는 api
                //post.content 넘겨주기
            }
            else if(num == 2)
            {
                try {
                    Thread.sleep(rand.nextInt(100)*10000); // 시간 늦추기
                } catch (InterruptedException e) {
                }
                // 내용 & 이미지 api
                //post.content 넘겨주기
            }
            //api 호출해서 content 넘기고 결과값을 받아옴
            //db에 저장
        }

        CommentEntity comment = CommentEntity.builder()
                .postId(post.get())
//                .userId(user.get())
//                .content(res)
                .build();

        return commentRepository.save(comment).getCommentId();
    }


}
