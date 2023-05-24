package com.example.narshaback.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 좋아요 id

    @CreatedDate
    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private UserEntity likeUser; // 유저 id

    @ManyToOne
    private PostEntity likePost; // 포스트 id
}
