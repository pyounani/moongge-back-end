package com.example.narshaback.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId; // 댓글 id

    @ManyToOne
    private GroupEntity groupCode; // 그룹 id

    @Column(columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @CreatedDate
    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private PostEntity postId; // 포스트
 }