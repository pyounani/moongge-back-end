package com.narsha.moongge.base.dto.notice;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoticeDTO {

    @NotEmpty
    private String groupCode;

    @NotEmpty
    private String noticeTitle;

    @NotEmpty
    private String noticeContent;

    @NotEmpty
    private String writer;
}