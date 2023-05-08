package com.example.narshaback.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Getter
@Setter
@Entity
public class UserEntity {
    @Id
    @Column(length=100)
    private String userId; // 유저 id

    @Column(length=100)
    private String password; // 패스워드

    @Column(length=20)
    private String userType; // 유저 타입: teacher | student

    @Column(length=20)
    private String nikname; // 닉네임

    @Column(nullable = false, length=500)
    private String badgeList; // 뱃지 리스트(string array)

    @Column(nullable = false, length=200)
    private String profileImage; // 프로필 이미지 링크

    @Column(nullable = false, length=20)
    private String birth; // 생일: ex) 2023.05.08

}
