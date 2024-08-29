package com.narsha.moongge.base.dto.notice;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoticeDTO {

    @NotEmpty(message = "noticeTitle를 입력해주세요.")
    private String noticeTitle;

    @NotEmpty(message = "noticeContent를 입력해주세요.")
    private String noticeContent;

    @NotEmpty(message = "writer를 입력해주세요.")
    private String writer;
}