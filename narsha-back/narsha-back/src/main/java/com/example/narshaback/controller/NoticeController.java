package com.example.narshaback.controller;

import com.example.narshaback.dto.notice.CreateNoticeDTO;
import com.example.narshaback.entity.NoticeEntity;
import com.example.narshaback.projection.notice.GetNotice;
import com.example.narshaback.service.NoticeService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/createNotice")
    public String createNotice(@RequestBody CreateNoticeDTO createNoticeDTO){
        Boolean res = noticeService.createNotice(createNoticeDTO);
        JsonObject obj = new JsonObject();
        obj.addProperty("success", res);

        if (res) obj.addProperty("message", "공지 등록에 성공했습니다!");
        else obj.addProperty("message", "해당 그룹코드는 존재하지 않습니다.");

        return obj.toString();
    }

    @GetMapping("/getNoticeList")
    public List<GetNotice> getNoticeList(@RequestParam(value = "groupId")String groupId){
        List<GetNotice> res = noticeService.getNoticeList(groupId);
        JsonObject obj = new JsonObject();

        if(res == null) {
            obj.addProperty("message", "작성한 공지가 없습니다.");
            //return obj.toString();
        }
        return res;

    }

    @GetMapping("/getNoticeDetail")
    public Optional<NoticeEntity> getNoticeDetail(@RequestParam(value = "noticeId")Integer noticeId){
        Optional<NoticeEntity> res = noticeService.getNoticeDetail(noticeId);
        JsonObject obj = new JsonObject();

//        if (res == null) obj.addProperty("message", "해당 공지를 찾을 수 없습니다.");
//        else obj.addProperty("message", "해당 공지를 찾을 수 없습니다.");

        return res;
    }
}
