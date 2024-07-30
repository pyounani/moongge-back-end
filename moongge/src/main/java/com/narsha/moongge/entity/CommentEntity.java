package com.narsha.moongge.entity;

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
    @JoinColumn(name = "comment_id")
    private Integer commentId; // 댓글 id

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "group_code")
    private GroupEntity group;

    @ManyToOne
    @JoinColumn(name = "writer")
    private UserEntity user; // 유저 id

    @Column(columnDefinition = "TEXT")
    @JoinColumn(name = "content")
    private String content; // 댓글 내용

    @CreatedDate
    @JoinColumn(name = "create_at")
    private LocalDateTime createAt; // 생성일

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post; // 포스트
 }