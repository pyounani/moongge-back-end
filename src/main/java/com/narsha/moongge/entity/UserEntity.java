package com.narsha.moongge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;


@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity implements Persistable<String> {

    @Id
    @Column(nullable = false, length=100)
    @JoinColumn(name = "user_id")
    private String userId; // 유저 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code")
    private GroupEntity group;

    @Column(nullable = false, length=100)
    @JoinColumn(name = "password")
    private String password; // 패스워드

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length=20)
    @JoinColumn(name = "user_type")
    private UserType userType; // 유저 타입: teacher | student

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

    @Transient
    @Builder.Default
    private boolean isNew = true;

    public void updateBadgeList(String badgeList) {
        this.badgeList = badgeList;
    }

    public void updateGroup(GroupEntity group) {
        this.group = group;
    }

    public void clearGroup() {
        this.group = null;
    }

    public void updateProfile(String birth, String nickname, String intro, String profileImage) {
        this.birth = birth;
        this.nickname = nickname;
        this.intro = intro;
        this.profileImage = profileImage;
    }

    @Override
    public String getId() {
        return this.userId;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @PostLoad
    @PrePersist
    private void markNotNew() {
        this.isNew = false;
    }
}
