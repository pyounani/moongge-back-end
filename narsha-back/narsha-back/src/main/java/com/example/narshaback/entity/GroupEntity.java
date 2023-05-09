package com.example.narshaback.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {
    @Id
    @Column(nullable = false, length=20)
    private String groupCode; // 그룹 코드

    @Column(nullable = false, length=20)
    private String groupName; // 그룹 이름

    @Column(nullable = false, length=40)
    private String school; // 학교 이름

    @Column(nullable = false)
    private Integer grade; // 학년

    @Column(nullable = false)
    private Integer group_class; // 반

    private LocalDateTime createAt; // 생성일
}