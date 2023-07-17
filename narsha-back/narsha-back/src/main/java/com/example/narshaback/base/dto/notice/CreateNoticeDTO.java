package com.example.narshaback.base.dto.notice;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
