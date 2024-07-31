package com.narsha.moongge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class GroupEntity {
    @Id
    @Column(nullable = false, length=20)
    @JoinColumn(name = "group_code")
    private String groupCode; // 그룹 코드

    @Column(nullable = false, length=20)
    @JoinColumn(name = "group_name")
    private String groupName; // 그룹 이름

    @Column(nullable = false, length=40)
    @JoinColumn(name = "school")
    private String school; // 학교 이름

    @Column(nullable = false)
    @JoinColumn(name = "grade")
    private Integer grade; // 학년

    @Column(nullable = false)
    @JoinColumn(name = "group_class")
    private Integer groupClass; // 반

    @Column
    @JoinColumn(name = "start_time")
    private LocalTime startTime;

    @Column
    @JoinColumn(name = "end_time")
    private LocalTime endTime;

    @CreatedDate
    @JoinColumn(name = "create_at")
    private LocalDateTime createAt; // 생성일

    public void setTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

}