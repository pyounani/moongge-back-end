package com.example.narshaback.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 댓글 id

    @Column(columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @CreatedDate
    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private PostEntity post; // 포스트 id
 }