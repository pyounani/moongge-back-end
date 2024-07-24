package com.narsha.moongge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    @JoinColumn(name = "group_code")
    private GroupEntity group; // 그룹 code

    @ManyToOne
    @JoinColumn(name = "writer")
    private UserEntity user; // 작성자

    @Column(nullable = false, length=100)
    private String noticeTitle; // 공지 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String noticeContent; // 공지 내용

    @CreatedDate
    private LocalDateTime createAt; // 생성일
}