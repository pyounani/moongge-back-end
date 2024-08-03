package com.narsha.moongge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "post_id")
    private Integer postId; // 포스트 id

    @ManyToOne
    @JoinColumn(name = "group_code")
    private GroupEntity group; // group code

    @ManyToOne
    @JoinColumn(name = "writer")
    private UserEntity user;  // 작성자

    @Column(nullable = false, columnDefinition = "TEXT")
    @JoinColumn(name = "content")
    private String content; // 포스트 내용

    @Column(columnDefinition = "TEXT")
    @JoinColumn(name = "image_array")
    private String imageArray; // 이미지 목록(String Array)

    @CreatedDate
    @JoinColumn(name = "create_at")
    private LocalDateTime createAt; // 생성일

}