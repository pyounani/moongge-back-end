package com.example.narshaback.entity;

import lombok.*;
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
public class AlarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer alarmId;

    private String actionType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity commentId;

    @ManyToOne
    @JoinColumn(name = "like_id")
    private LikeEntity likeId;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private NoticeEntity noticeId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postId;

    @ManyToOne
    @JoinColumn(name = "group_code")
    private GroupEntity groupCode;

    private LocalDateTime createdAt;


}
