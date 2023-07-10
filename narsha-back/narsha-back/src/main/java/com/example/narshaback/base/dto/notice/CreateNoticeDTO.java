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
<<<<<<< HEAD:narsha-back/narsha-back/src/main/java/com/example/narshaback/dto/notice/CreateNoticeDTO.java
    private String noticeTitle;
=======
    private String userId;
>>>>>>> edabb70 ([feat] comment, like 에러 처리 추가):narsha-back/narsha-back/src/main/java/com/example/narshaback/base/dto/comment/CreateCommentDTO.java

    @NotNull
    private String noticeContent;

    @NotNull
    private String writer;
}
