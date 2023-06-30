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
    private User_Group userGroupId; // 좋아요를 누른 유저-그룹

    @ManyToOne
    private PostEntity postId; // 포스트 id
}
