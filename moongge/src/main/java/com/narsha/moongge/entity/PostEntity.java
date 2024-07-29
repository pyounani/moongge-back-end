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
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId; // 포스트 id

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "group_code")
    private GroupEntity group; // group code

    @ManyToOne
    @JoinColumn(name = "writer")
    private UserEntity user;  // 작성자

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 포스트 내용

    @Column(columnDefinition = "TEXT")
    private String imageArray; // 이미지 목록(String Array)

    @CreatedDate
    private LocalDateTime createAt; // 생성일

}