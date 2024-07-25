package com.narsha.moongge.base.dto.notice;

import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.NoticeEntity;
import com.narsha.moongge.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NoticeDTO {

    private Integer noticeId;
    private String groupCode;
    private String writer;
    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime createAt;

    public static NoticeDTO mapToNoticeDTO(NoticeEntity notice) {
        return NoticeDTO.builder()
                .noticeId(notice.getNoticeId())
                .groupCode(notice.getGroup().getGroupCode())
                .writer(notice.getUser().getUserId())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
                .createAt(notice.getCreateAt())
                .build();
    }
}
