package com.example.narshaback.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 공지 id

    @ManyToOne
    private GroupEntity groupId; // 그룹 id

    @Column(nullable = false, length=100)
    private String noticeTitle; // 공지 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String noticeContent; // 공지 내용

    @CreatedDate
    private LocalDateTime createAt; // 생성일

    @ManyToOne
    private UserEntity writer; // 작성자
}