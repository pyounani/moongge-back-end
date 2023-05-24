package com.example.narshaback.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder // DTO -> Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 프로필 고유 id

    @OneToOne
    private User_Group user_group; // 유저-그룹 id

    @Column(length=20)
    private String nikname; // 닉네임

    @Column(length=200)
    private String profileImage; // 프로필 이미지 링크

    @Column(length=20)
    private String birth; // 생일: ex) 2023.05.08

    @Column(length=100)
    private String intro; // 프로필 소개

    @Column(length=500)
    private String badgeList; // 뱃지 리스트(string array)
}
