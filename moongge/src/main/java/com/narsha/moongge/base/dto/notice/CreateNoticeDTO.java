package com.narsha.moongge.base.dto.notice;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoticeDTO {

    @NotNull
    private String groupCode;

    @NotNull
    private String noticeTitle;

    @NotNull
    private String noticeContent;

    @NotNull
    private String writer;
}