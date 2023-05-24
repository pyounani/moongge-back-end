package com.example.narshaback.dto.notice;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoticeDTO {
    @NotNull
    private String groupId;

    @NotNull
    private String noticeTitle;

    @NotNull
    private String noticeContent;
}
