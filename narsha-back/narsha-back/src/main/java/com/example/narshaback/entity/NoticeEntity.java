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
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeId; // 공지 id

    @ManyToOne(cascade = CascadeType.REMOVE)
    private GroupEntity groupCode; // 그룹 code

    @ManyToOne
    private UserEntity writer; // 작성자

    @Column(nullable = false, length=100)
    private String noticeTitle; // 공지 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String noticeContent; // 공지 내용

    @CreatedDate
    private LocalDateTime createAt; // 생성일
}