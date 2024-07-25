package com.narsha.moongge.controller;

import com.google.gson.JsonObject;
import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.notice.CreateNoticeDTO;
import com.narsha.moongge.base.dto.notice.NoticeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.projection.notice.GetNotice;
import com.narsha.moongge.base.projection.notice.GetRecentNotice;
import com.narsha.moongge.entity.NoticeEntity;
import com.narsha.moongge.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
                                                    @RequestBody CreateNoticeDTO createNoticeDTO){
        NoticeDTO res = noticeService.createNotice(groupCode, createNoticeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_NOTICE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_NOTICE, res));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getNoticeList(@RequestParam(value = "groupCode")String groupCode){
        List<GetNotice> res = noticeService.getNoticeList(groupCode);
        JsonObject obj = new JsonObject();

        if(res == null) {
            obj.addProperty("message", "작성한 공지가 없습니다.");
            //return obj.toString();
        }
        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_LIST, res));

    }

    @GetMapping("/detail")
    public ResponseEntity<ResponseDTO> getNoticeDetail(@RequestParam(value = "noticeId")Integer noticeId){
        Optional<NoticeEntity> res = noticeService.getNoticeDetail(noticeId);
        JsonObject obj = new JsonObject();

//       if (res == null) obj.addProperty("message", "해당 공지를 찾을 수 없습니다.");
//       else obj.addProperty("message", "해당 공지를 찾을 수 없습니다.");

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_DETAIL.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_DETAIL, res));
    }

    @GetMapping("/recent-one")
    public ResponseEntity<ResponseDTO> getRecentNoticeOne(@RequestParam(value = "groupCode")String groupCode){
        Optional<GetRecentNotice> res = noticeService.getRecentNoticeOne(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_NOTICE_RECENT_ONE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_NOTICE_RECENT_ONE, res));
    }
}
