package com.example.narshaback.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    private User_Group userGroupId; // user-group id

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 포스트 내용

    @Column(columnDefinition = "TEXT")
    private String imageArray; // 이미지 목록(String Array)

    @CreatedDate
    private LocalDateTime createAt; // 생성일

    // 사용 여부 추후에 결정
    // Comment 엔티티: post 객체로만 관리 가능, 이 클래스는 조회만
    // @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    // private List<CommentEntity> commentList; // 댓글 목록
}