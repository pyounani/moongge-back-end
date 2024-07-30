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
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "like_id")
    private Integer likeId; // 좋아요 id

    @CreatedDate
    @JoinColumn(name = "create_at")
    private LocalDateTime createAt; // 생성일

    @ManyToOne
    @JoinColumn(name = "group_code")
    private GroupEntity group; // 좋아요를 누른 유저의 그룹

    @OneToOne
    @JoinColumn(name = "writer")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post; // 포스트 id
}
