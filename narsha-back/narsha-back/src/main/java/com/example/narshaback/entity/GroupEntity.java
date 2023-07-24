package com.example.narshaback.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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
    private Integer groupClass; // 반

    @Column(length=30)
    private String startTime;

    @Column(length=30)
    private String endTime;

    @CreatedDate
    private LocalDateTime createAt; // 생성일
}