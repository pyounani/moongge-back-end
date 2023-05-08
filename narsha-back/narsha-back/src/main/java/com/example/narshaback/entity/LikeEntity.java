package com.example.narshaback.entity;

import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 좋아요 id

    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private UserEntity likeUser; // 유저 id

    @ManyToOne
    private PostEntity likePost; // 포스트 id
}
