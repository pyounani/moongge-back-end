package com.example.narshaback.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 포스트 id

    @ManyToOne
    private GroupEntity groupId; // 그룹 id

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 포스트 내용

    @Column(columnDefinition = "TEXT")
    private String imageArray; // 이미지 목록(String Array)

    @CreatedDate
    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private UserEntity writer; // 작성자(User)

    // Comment 엔티티: post 객체로만 관리 가능, 이 클래스는 조회만
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<CommentEntity> commentList; // 댓글 목록
}