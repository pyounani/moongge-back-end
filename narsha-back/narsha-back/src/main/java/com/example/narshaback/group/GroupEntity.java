package com.example.narshaback.group;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class GroupEntity {
    @Id
    @Column(length=20)
    private String groupCode; // 그룹 코드

    @Column(length=20)
    private String groupName; // 그룹 이름

    @Column(length=40)
    private String school; // 학교 이름

    private Integer grade; // 학년

    private Integer group_class; // 반

    private LocalDateTime createAt; // 생성일
}