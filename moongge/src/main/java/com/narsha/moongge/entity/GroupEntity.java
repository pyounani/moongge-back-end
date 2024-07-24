package com.narsha.moongge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @CreatedDate
    private LocalDateTime createAt; // 생성일

    public void setTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

}