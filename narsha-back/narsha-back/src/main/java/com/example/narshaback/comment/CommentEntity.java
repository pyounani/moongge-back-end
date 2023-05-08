package com.example.narshaback.comment;

import com.example.narshaback.post.PostEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 댓글 id

    @Column(columnDefinition = "TEXT")
    private String content; // 댓글 내용

    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private PostEntity post; // 포스트 id
}