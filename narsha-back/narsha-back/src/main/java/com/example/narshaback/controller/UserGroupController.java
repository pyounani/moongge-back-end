package com.example.narshaback.controller;

import com.example.narshaback.dto.JoinGroupDTO;
import com.example.narshaback.service.UserGroupService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
public class UserGroupController {
    private final UserGroupService userGroupService;

    @PostMapping("/joinGroup")
    public String joinGroup(JoinGroupDTO joinGroupDTO){
        Boolean res = userGroupService.joinUser(joinGroupDTO);
        JsonObject obj = new JsonObject();
        obj.addProperty("res", res);

        if (res == null)  obj.addProperty("message", "해당 그룹이 존재하지 않습니다.");
        else obj.addProperty("message", "그룹 가입이 완료되었습니다.");

        return obj.toString();
    }
}
