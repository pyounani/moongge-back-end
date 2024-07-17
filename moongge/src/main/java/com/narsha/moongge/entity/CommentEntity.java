package com.narsha.moongge.entity;

import com.narsha.moongge.group.GroupEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @ManyToOne(cascade = CascadeType.REMOVE)
    private GroupEntity groupCode;

    @ManyToOne
    private UserEntity userId; // 유저 id

    @Column(columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @CreatedDate
    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private PostEntity postId; // 포스트
 }