package com.narsha.moongge.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Builder // DTO -> Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @Column(nullable = false, length=100)
    @JoinColumn(name = "user_id")
    private String userId; // 유저 id

    @ManyToOne
    @JoinColumn(name = "group_code")
    private GroupEntity group;

    @Column(nullable = false, length=100)
    @JoinColumn(name = "password")
    private String password; // 패스워드

    @Column(nullable = false, length=20)
    @JoinColumn(name = "user_type")
    private String userType; // 유저 타입: teacher | student

    @Column(nullable = false, length=20)
    @JoinColumn(name = "user_name")
    private String userName; // 이름

    @Column(length=20)
    @JoinColumn(name = "nickname")
    private String nickname; // 닉네임

    @Column(length=200)
    @JoinColumn(name = "profile_image")
    private String profileImage; // 프로필 이미지 링크

    @Column(length=20)
    @JoinColumn(name = "birth")
    private String birth; // 생일: ex) 2023.05.08

    @Column(length=100)
    @JoinColumn(name = "intro")
    private String intro; // 프로필 소개

    @Column(length=500)
    @JoinColumn(name = "badge_list")
    private String badgeList; // 뱃지 리스트(string array)

    @Column(length=200)
    @JoinColumn(name = "fcm_token")
    private String fcmToken; // FCM 토큰 필드

    public void updateBadgeList(String badgeList) {
        this.badgeList = badgeList;
    }

    public void updateGroup(GroupEntity group) {
        this.group = group;
    }

    public void clearGroup() {
        this.group = null;
    }
}
