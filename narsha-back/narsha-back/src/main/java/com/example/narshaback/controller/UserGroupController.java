package com.example.narshaback.controller;

import com.example.narshaback.base.code.ResponseCode;
import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.dto.response.ResponseDTO;
import com.example.narshaback.base.projection.user.GetUserInGroup;
import com.example.narshaback.base.projection.user_group.GetJoinGroupList;
import com.example.narshaback.service.UserGroupService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDTO> joinGroup(@RequestBody JoinGroupDTO joinGroupDTO){
        Boolean res = userGroupService.joinUser(joinGroupDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_JOIN_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_JOIN_GROUP, res));
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
