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
@Table(name = "likeentity", uniqueConstraints = {@UniqueConstraint(columnNames = {"writer", "post_id"})})
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "like_id")
    private Integer likeId; // 좋아요 id

    @CreatedDate
    @JoinColumn(name = "create_at")
    private LocalDateTime createAt; // 생성일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code")
    private GroupEntity group; // 좋아요를 누른 유저의 그룹

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post; // 포스트 id
}
