package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                                  @RequestBody UpdateTimeDTO updateTimeDTO){
        UpdateTimeDTO res = groupService.updateTime(groupCode, updateTimeDTO);

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
