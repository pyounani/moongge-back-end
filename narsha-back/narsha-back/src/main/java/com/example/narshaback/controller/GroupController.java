package com.example.narshaback.controller;

import com.example.narshaback.dto.group.CreateGroupDTO;
import com.example.narshaback.service.GroupService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/create")
    public String createGroup(@RequestBody CreateGroupDTO createGroupDTO){
        Integer res = groupService.createGroup(createGroupDTO);
        JsonObject obj = new JsonObject();
        obj.addProperty("user-groupId", res);
        if (res == null) obj.addProperty("message", "그룹 생성 실패");
        else obj.addProperty("message", "그룹 생성 성공");

        return obj.toString();
    }

}
