package com.example.narshaback.controller;

import com.example.narshaback.base.code.ResponseCode;
import com.example.narshaback.base.dto.notice.CreateNoticeDTO;
import com.example.narshaback.base.dto.response.ResponseDTO;
import com.example.narshaback.entity.NoticeEntity;
import com.example.narshaback.base.projection.notice.GetNotice;
import com.example.narshaback.base.projection.notice.GetRecentNotice;
import com.example.narshaback.service.NoticeService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createNotice(@RequestBody CreateNoticeDTO createNoticeDTO){
        Boolean res = noticeService.createNotice(createNoticeDTO);

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
