package com.narsha.moongge.service;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.comment.CommentDTO;
import com.narsha.moongge.base.dto.comment.CreateCommentDTO;
import com.narsha.moongge.base.exception.*;
import com.narsha.moongge.entity.CommentEntity;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.CommentRepository;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.PostRepository;
import com.narsha.moongge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    WebClient webClient = WebClient.create("http://localhost:8000");
    Random rand = new Random();

    /**
     * 댓글 작성하기
     */
    @Override
    @Transactional
    public Integer createComment(String userId, Integer postId, CreateCommentDTO createCommentDTO) {

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, user.getGroup())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        // 댓글에 내용이 없을 경우
        validateCommentContent(createCommentDTO);

        CommentEntity comment = CommentEntity.builder()
                .post(post)
                .user(user)
                .group(user.getGroup())
                .content(createCommentDTO.getContent())
                .build();

        CommentEntity savedComment = commentRepository.save(comment);

        return savedComment.getCommentId();
    }

    /**
     * 특정 포스트 댓글 목록 불러오기
     */
    @Override
    public List<CommentDTO> getCommentList(String groupCode, Integer postId) {

        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, group)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        List<CommentEntity> commentList = commentRepository.findByPost(post);

        return commentList.stream()
                .map(CommentDTO::mapToCommentDTO)
                .collect(Collectors.toList());
    }

    /**
     * 최신 댓글 1개 가져오기
     */
    @Override
    public CommentDTO getRecentComment(String groupCode, Integer postId) {

        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, group)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        CommentEntity findComment = commentRepository.findTopByPostOrderByCreateAtDesc(post)
                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        return CommentDTO.mapToCommentDTO(findComment);
    }

    /**
     * 특정 포스트 댓글 갯수 가져오기
     */
    @Override
    public Long countComment(String userId, Integer postId){

        UserEntity user = userRepository.findUserWithGroup(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        PostEntity post = postRepository.findByPostIdAndGroup(postId, user.getGroup())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        Long countComments = commentRepository.countByPost(post);

        return countComments;
    }

    @Override
    @Transactional
    public String createAIComment(Integer postId) {

        //포스트 검색
        Optional<PostEntity> post = postRepository.findByPostId(postId);
        if (!post.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }

        //포스트 내용 가져와서 json 형태로
        String post_content = "{\"post_content\": \""+ post.get().getContent() +"\"}";
        String result="";

        int friend = 1;
        int teacher = rand.nextInt(2);
        int disgust = rand.nextInt(2);
        int senior = rand.nextInt(2);

        if (friend == 1){ // 친구 AI봇
            Optional<UserEntity> user = userRepository.findByUserId("ai_1");

            int num = rand.nextInt(3);
            if(num == 0){ // 글 내용만 전달 받는 api
                //thread 지연
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }
                String uri = "/chat/friend/content";
                String res = postContent(post_content, uri);

                //comment 저장
                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";

            } else if(num == 1) {// 이미지만 전달 받는 api
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }

                String uri = "/chat/friend/image";
                String aws_image = awsImage(post);
                String post_image = "{\"post_image\": \""+ aws_image +"\"}";
                System.out.println(post_image);

                String res = postImage(post_image, uri);

                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            } else if(num == 2) {// 내용 & 이미지 전달 받는 api
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }

                String uri = "/chat/friend/image-content";
                String aws_image = awsImage(post);
                String post_image_content = "{\"post_content\": \""+ post.get().getContent() +"\",\n \"post_image\": \""+aws_image+"\"}";

                System.out.println(post_image_content);

                String res = postImageContent(post_image_content, uri);

                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            }
        }

        if (teacher == 1) { //선생님 AI봇

            Optional<UserEntity> user = userRepository.findByUserId("ai_2");

            int num = rand.nextInt(3);

            if(num == 0){ // 글 내용만 전달 받는 api
                //thread 지연
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }
                String uri = "/chat/teacher/content";
                String res = postContent(post_content, uri);

                //comment 저장
                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            }  else if(num == 1) {// 이미지만 전달 받는 api
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }

                String uri = "/chat/teacher/image";
                String aws_image = awsImage(post);
                String post_image = "{\"post_image\": \""+ aws_image +"\"}";
                System.out.println(post_image);

                String res = postImage(post_image, uri);

                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            }  else if(num == 2) {// 내용 & 이미지 전달 받는 api
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }

                String uri = "/chat/teacher/image/content";
                String aws_image = awsImage(post);
                String post_image_content = "{\"post_content\": \""+ post.get().getContent() +"\",\n \"post_image\": \""+aws_image+"\"}";

                System.out.println(post_image_content);

                String res = postImageContent(post_image_content, uri);

                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            }

        }

        if (disgust == 1){ // 까칠한 친구 AI봇
            Optional<UserEntity> user = userRepository.findByUserId("ai_3");

            int num = rand.nextInt(3);
            if(num == 0){ // 글 내용만 전달 받는 api
                //thread 지연
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }
                String uri = "/chat/disgust/content";
                String res = postContent(post_content, uri);

                //comment 저장
                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            }else if(num == 1) {// 이미지만 전달 받는 api
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }

                String uri = "/chat/disgust/image";
                String aws_image = awsImage(post);
                String post_image = "{\"post_image\": \""+ aws_image +"\"}";
                System.out.println(post_image);

                String res = postImage(post_image, uri);

                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            }  else if(num == 2) {// 내용 & 이미지 전달 받는 api
//                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
//                } catch (InterruptedException e) {
//                }

                String uri = "/chat/disgust/image-content";
                String aws_image = awsImage(post);
                String post_image_content = "{\"post_content\": \""+ post.get().getContent() +"\",\n \"post_image\": \""+aws_image+"\"}";

                System.out.println(post_image_content);

                String res = postImageContent(post_image_content, uri);

                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            }
        }

        if (senior == 1){ // 선배 AI봇
            Optional<UserEntity> user = userRepository.findByUserId("ai_4");

            int num = rand.nextInt(3);
            if(num == 0){ // 글 내용만 전달 받는 api
//                thread 지연
                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
                    Thread.sleep(25000);
                } catch (InterruptedException e) {
                }
                String uri = "/chat/senior/content";
                String res = postContent(post_content, uri);

                //comment 저장
                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            } else if(num == 1) {// 이미지만 전달 받는 api
                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
                    Thread.sleep(25000);
                } catch (InterruptedException e) {
                }
                String uri = "/chat/senior/image";
                String aws_image = awsImage(post);
                String post_image = "{\"post_image\": \""+ aws_image +"\"}";
                System.out.println(post_image);

                String res = postImage(post_image, uri);

                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            }  else if(num == 2) {// 내용 & 이미지 전달 받는 api
                try {
//                    Thread.sleep(rand.nextInt(100)*10000);
                    Thread.sleep(25000);
                } catch (InterruptedException e) {
                }

                String uri = "/chat/senior/image-content";
                String aws_image = awsImage(post);
                String post_image_content = "{\"post_content\": \""+ post.get().getContent() +"\",\n \"post_image\": \""+aws_image+"\"}";

                System.out.println(post_image_content);

                String res = postImageContent(post_image_content, uri);

                CommentEntity comment = CommentEntity.builder()
                        .post(post.get())
                        .user(user.get())
                        .group(user.get().getGroup())
                        .content(res)
                        .build();

                result+=commentRepository.save(comment).getCommentId()+", ";
            }
        }

        return result;
    }

    public String postContent (String post_content, String uri){ //포스트 내용 댓글

        String res = webClient.post()
                .uri(uri)
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

    public String postImage (String post_image, String uri){ // 포스트 이미지 댓글

        String res = webClient.post()
                .uri(uri)
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

    public String postImageContent (String post_image_content, String uri){ // 포스트 이미지 댓글

        String res = webClient.post()
                .uri(uri)
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

        String imageData =post.get().getImageArray().substring(1, post.get().getImageArray().length()-1);
        String [] imageArray = imageData.split(", ");
        for(int i=0; i< imageArray.length;i++){
            System.out.println(imageArray[i]);
        }
        int num = rand.nextInt(imageArray.length);

        System.out.println(num);
        String image = imageArray[num].substring(57, imageArray[num].length());
        String keyword="";

        System.out.println(image);

        String res = webClient.get()
                .uri("/rekognition/detect-label?filename={image}", image)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONArray jsonArray = new JSONArray(res);
        JSONObject aws = jsonArray.getJSONObject(0);
        JSONArray label = aws.getJSONArray("label");

        for(int i=0; i<label.length(); i++){
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

    private void validateCommentContent(CreateCommentDTO createCommentDTO) {
        String content = createCommentDTO.getContent();
        if(content == null || content.trim().isEmpty()) {
            throw new EmptyCommentContentException(ErrorCode.EMPTY_COMMENT_CONTENT);
        }
    }

}
