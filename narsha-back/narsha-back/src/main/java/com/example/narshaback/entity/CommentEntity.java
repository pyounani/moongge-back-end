package com.example.narshaback.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    private Integer id; // 댓글 id
=======
    private Integer commentId; // 댓글 id

    @ManyToOne
    private GroupEntity groupCode;

    @ManyToOne
    private UserEntity userId; // 유저 id
>>>>>>> edabb70 ([feat] comment, like 에러 처리 추가)

    @Column(columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @CreatedDate
    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private PostEntity post; // 포스트 id
 }