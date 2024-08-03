package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.group.GroupDTO;
import com.narsha.moongge.base.dto.group.JoinGroupDTO;
import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.dto.user.UserProfileDTO;
import com.narsha.moongge.base.projection.user.GetUser;
import com.narsha.moongge.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    /**
     * 그룹 생성 API
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> createGroup(@Valid @RequestBody CreateGroupDTO createGroupDTO){
        String res = groupService.createGroup(createGroupDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_GROUP, res));
    }

    /**
     * 유저가 속해있는 그룹 코드 가져오기 API
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseDTO> getUserGroupCode(@PathVariable String userId) {
        String groupCode = groupService.getUserGroupCode(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_GROUP_CODE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_GROUP_CODE, groupCode));
    }

    /**
     * 그룹 가입하기 API
     */
    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> joinGroup(@Valid @RequestBody JoinGroupDTO joinGroupDTO){
        GroupDTO res = groupService.joinGroup(joinGroupDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_JOIN_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_JOIN_GROUP, res));
    }

    /**
     * 그룹 삭제하기 API
     */
    @DeleteMapping("/{groupCode}")
    public ResponseEntity<ResponseDTO> deleteGroup(@PathVariable String groupCode) {
        String res = groupService.deleteGroup(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_DELETE_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_DELETE_GROUP, res));
    }

    /**
     * 그룹 시간 등록(수정)하기 API
     */
    @PutMapping("/{groupCode}/time")
    public ResponseEntity<ResponseDTO> updateTime(@PathVariable String groupCode,
                                                  @Valid @RequestBody UpdateTimeDTO updateTimeDTO){
        UpdateTimeDTO res = groupService.updateTime(groupCode, updateTimeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPDATE_TIME.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPDATE_TIME, res));
    }

    /**
     * 설정한 그룹 시간 불러오기 API
     */
    @GetMapping("/{groupCode}/time")
    public ResponseEntity<ResponseDTO> getTime(@PathVariable String groupCode){
        UpdateTimeDTO res = groupService.getTime(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_TIME.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_TIME, res));
    }

    /**
     * 그룹의 유저 목록 가져오기(요청한 유저 제외) API
     */
    @GetMapping("/{groupCode}/users/{userId}/list")
    public ResponseEntity<ResponseDTO> getStudentList(@PathVariable String groupCode,
                                                      @PathVariable String userId) {
        List<UserProfileDTO> res = groupService.getStudentList(groupCode, userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_USER_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_USER_LIST, res));

    }

}
