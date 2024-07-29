package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.notice.CreateNoticeDTO;
import com.narsha.moongge.base.dto.notice.NoticeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.service.NoticeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 공지 작성하기 API
     */
    @PostMapping("/{groupCode}")
    public ResponseEntity<ResponseDTO> createNotice(@PathVariable String groupCode,
                                                    @Valid @RequestBody CreateNoticeDTO createNoticeDTO){
        NoticeDTO res = noticeService.createNotice(groupCode, createNoticeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_NOTICE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_NOTICE, res));
    }

    /**
     * 공지 목록 불러오기 API
     */
    @GetMapping("/{groupCode}")
    public ResponseEntity<ResponseDTO> getNoticeList(@PathVariable String groupCode) {
        List<NoticeDTO> res = noticeService.getNoticeList(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_LIST, res));
    }

    /**
     * 공지 상세사항 내용 불러오기 API
     */
    @GetMapping("/{groupCode}/{noticeId}")
    public ResponseEntity<ResponseDTO> getNoticeDetail(@PathVariable String groupCode,
                                                       @PathVariable Integer noticeId) {
        NoticeDTO res = noticeService.getNoticeDetail(groupCode, noticeId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_DETAIL.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_DETAIL, res));
    }

    /**
     * 최근에 올린 공지 한 개 API
     */
    @GetMapping("/{groupCode}/recent")
    public ResponseEntity<ResponseDTO> getRecentNoticeOne(@PathVariable String groupCode) {
        NoticeDTO res = noticeService.getRecentNoticeOne(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_RECENT_ONE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_RECENT_ONE, res));
    }
}
