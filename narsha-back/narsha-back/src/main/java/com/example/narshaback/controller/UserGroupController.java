package com.example.narshaback.controller;

import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.projection.user.GetUserInGroup;
import com.example.narshaback.base.projection.user_group.GetJoinGroupList;
import com.example.narshaback.service.UserGroupService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user-group")
public class UserGroupController {
    private final UserGroupService userGroupService;

    @PostMapping("/join")
    public String joinGroup(@RequestBody JoinGroupDTO joinGroupDTO){
        Boolean res = userGroupService.joinUser(joinGroupDTO);
        JsonObject obj = new JsonObject();
        obj.addProperty("res", res);

        if (res == false)  obj.addProperty("message", "해당 그룹이 존재하지 않습니다.");
        else obj.addProperty("message", "그룹 가입이 완료되었습니다.");

        return obj.toString();
    }

    @GetMapping("/user-list-in")
    public List<GetUserInGroup> getUserListInGroup(@RequestParam(value = "groupCode")String groupCode){
        List<GetUserInGroup> res = userGroupService.getUserListInGroup(groupCode);
        return res;
    }

    @GetMapping("/group-code")
    public String getUserGroupCode(@RequestParam(value = "user-groupCode")Integer id){
        String res = userGroupService.getUserGroupCode(id);

        return res;
    }

    @GetMapping("/join-group-list")
    public List <GetJoinGroupList> joinGroupList(@RequestParam(value="userId")String userId){
        List <GetJoinGroupList> res = userGroupService.getJoinGroupList(userId);

        return res;
    }
}
