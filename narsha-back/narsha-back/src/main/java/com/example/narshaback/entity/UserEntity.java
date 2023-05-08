package com.example.narshaback.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder // DTO -> Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 유저 고유 num

    @Column(nullable = false, length=100)
    private String userId; // 유저 id

    @Column(nullable = false, length=100)
    private String password; // 패스워드

    @Column(nullable = false, length=20)
    private String userType; // 유저 타입: teacher | student

    @Column(nullable = false, length=20)
    private String name; // 이름

    @Column(nullable = false, length=20)
    private String nikname; // 닉네임

    @Column(length=500)
    private String badgeList; // 뱃지 리스트(string array)

    @Column(length=200)
    private String profileImage; // 프로필 이미지 링크

    @Column(length=20)
    private String birth; // 생일: ex) 2023.05.08

}
