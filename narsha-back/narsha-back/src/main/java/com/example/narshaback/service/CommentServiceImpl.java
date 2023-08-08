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
import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

    WebClient webClient = WebClient.create("http://localhost:8000");
//    WebClient webClient = WebClient.builder()
//        .baseUrl("http://localhost:8000")
//        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//        .build();
    Random rand = new Random();

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
                .groupCode(user.get().getGroupCode())
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

        //포스트 검색
        Optional<PostEntity> post = postRepository.findByPostId(postId);
        if (!post.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
        }

        //포스트 내용 가져와서 json 형태로
        String post_content = "{\"post_content\": \""+ post.get().getContent() +"\"}";

        int teacher =1;
//        int teacher = rand.nextInt(1);
        if (teacher == 1) { //선생님 AI봇

            Optional<UserEntity> user = userRepository.findByUserId("narsha1111");

            int num = rand.nextInt(2);

            if(num == 0){ // 글 내용만 전달 받는 api
                //thread 지연
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }
                String res = postContent(post_content);

                //comment 저장
                CommentEntity comment = CommentEntity.builder()
                        .postId(post.get())
                        .userId(user.get())
                        .groupCode(user.get().getGroupCode())
                        .content(res)
                        .build();

                return commentRepository.save(comment).getCommentId();
            }
            else if(num == 1) {// 이미지만 전달 받는 api
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }

                String aws_image = awsImage(post);
                String post_image = "{\"post_image\": \""+ aws_image +"\"}";
                System.out.println(post_image);

                String res = postImage(post_image);

                CommentEntity comment = CommentEntity.builder()
                        .postId(post.get())
                        .userId(user.get())
                        .groupCode(user.get().getGroupCode())
                        .content(res)
                        .build();

                return commentRepository.save(comment).getCommentId();
            }
            else if(num == 2) {// 내용 & 이미지 전달 받는 api
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }

                String aws_image = awsImage(post);
                String post_image_content = "{\"post_content\": \""+ post.get().getContent() +"\",\n \"post_image\": \""+aws_image+"\"}";

                System.out.println(post_image_content);

                String res = postImageContent(post_image_content);

                CommentEntity comment = CommentEntity.builder()
                        .postId(post.get())
                        .userId(user.get())
                        .groupCode(user.get().getGroupCode())
                        .content(res)
                        .build();

                return commentRepository.save(comment).getCommentId();
            }

        }

        CommentEntity comment = CommentEntity.builder()
                .postId(post.get())
//                .userId(user.get())
//                .content(res)
                .build();

        return commentRepository.save(comment).getCommentId();
    }

    public String postContent (String post_content){ //포스트 내용 댓글

        String res = webClient.post()
                .uri("/chat/teacher/content")
                .acceptCharset(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromValue(post_content))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        res = res.substring(11, res.length()-3);

        return uniToKor(res);
    }

    public String postImage (String post_image){ // 포스트 이미지 댓글

        String res = webClient.post()
                .uri("/chat/teacher/image")
                .acceptCharset(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromValue(post_image))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        res = res.substring(11, res.length()-3);

        return uniToKor(res);
    }

    public String postImageContent (String post_image_content){ // 포스트 이미지 댓글

        String res = webClient.post()
                .uri("/chat/teacher/image/content")
                .acceptCharset(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(post_image_content))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        res = res.substring(11, res.length()-3);

        return uniToKor(res);
    }

    public String awsImage (Optional<PostEntity> post){ // 이미지 키워드 추출

        String image = post.get().getImageArray().substring(1, post.get().getImageArray().length()-1);
        String a = "image.jpg"; // 추후 삭제
        String keyword="";

        String res = webClient.get()
                .uri("/rekognition/detect-label?filename={image}", a)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONArray jsonArray = new JSONArray(res);
        JSONObject aws = jsonArray.getJSONObject(0);
        JSONArray label = aws.getJSONArray("label");

        for(int i=0; i<5; i++){
            JSONObject jsonObject = label.getJSONObject(i);
            System.out.println(jsonObject);
            String value = jsonObject.getString("Name");
            keyword = keyword +", "+value;
        }

        keyword = keyword.substring(2);
        System.out.println(keyword);

        return keyword;
    }

    public String uniToKor(String uni){ // 유니코드 한글로 바꾸는 함수
        StringBuffer result = new StringBuffer();

        for(int i=0; i<uni.length(); i++){
            if(uni.charAt(i) == '\\' &&  uni.charAt(i+1) == 'u'){
                Character c = (char)Integer.parseInt(uni.substring(i+2, i+6), 16);
                result.append(c);
                i+=5;
            }else{
                result.append(uni.charAt(i));
            }
        }
        return result.toString();
    }


}
