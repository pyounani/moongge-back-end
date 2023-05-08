package com.example.narshaback.notice;

import com.example.narshaback.group.GroupEntity;
import com.example.narshaback.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 공지 id

    @ManyToOne
    private GroupEntity groupId; // 그룹 id

    @Column(length=100)
    private String noticeTitle; // 공지 제목

    @Column(columnDefinition = "TEXT")
    private String noticeContent; // 공지 내용

    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private UserEntity writer; // 작성자
}