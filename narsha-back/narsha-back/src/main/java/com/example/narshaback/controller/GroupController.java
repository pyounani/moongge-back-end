package com.example.narshaback.controller;

import com.example.narshaback.base.code.ResponseCode;
import com.example.narshaback.base.dto.group.CreateGroupDTO;
import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.dto.group.UpdateTimeDTO;
import com.example.narshaback.base.dto.response.ResponseDTO;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.service.GroupService;
import com.example.narshaback.service.UserService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createGroup(@RequestBody CreateGroupDTO createGroupDTO){
        String userId = groupService.createGroup(createGroupDTO);

//        JsonObject obj = new JsonObject();
//        obj.addProperty("user-groupId", res.toString());
//        if (res == null) obj.addProperty("message", "그룹 생성 실패");
//        else obj.addProperty("message", "그룹 생성 성공");
//
//        return obj.toString();


        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_GROUP, userId));
    }

    @GetMapping("/group-code")
    public ResponseEntity<ResponseDTO> getUserGroupCode(@RequestParam(value = "userId")String userId){
        String res = groupService.getUserGroupCode(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_GROUP_CODE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_GROUP_CODE, res));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteGroup(@RequestParam(value = "groupCode")String groupCode){

        String res = groupService.deleteGroup(groupCode);


        return ResponseEntity
                .status(ResponseCode.SUCCESS_DELETE_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_DELETE_GROUP, res));
    }

    @PutMapping("/update-time")
    public ResponseEntity<ResponseDTO> updateTime(@RequestBody UpdateTimeDTO updateTimeDTO){

        UpdateTimeDTO res = groupService.updateTime(updateTimeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPDATE_TIME.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPDATE_TIME, res));
    }

    @GetMapping("/get-time")
    public ResponseEntity<ResponseDTO> getTime(@RequestParam(value = "groupCode") String groupCode){

        UpdateTimeDTO res = groupService.getTime(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_TIME.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_TIME, res));
    }

}
