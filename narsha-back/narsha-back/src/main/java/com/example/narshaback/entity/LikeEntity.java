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
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId; // 좋아요 id

    @CreatedDate
    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private GroupEntity groupCode; // 좋아요를 누른 유저의 그룹

    @OneToOne
    private UserEntity userId;

    @ManyToOne
    private PostEntity postId; // 포스트 id
}
