package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.service.GroupServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupServiceImpl groupServiceImpl;

    /**
     * 그룹 생성 API
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createGroup(@Valid @RequestBody CreateGroupDTO createGroupDTO){
        String userId = groupServiceImpl.createGroup(createGroupDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_GROUP, userId));
    }

    /**
     * 유저가 속해있는 그룹 코드 가져오기 API
     */
    @GetMapping("/group-code")
    public ResponseEntity<ResponseDTO> getUserGroupCode(@RequestParam @NotEmpty String userId) {
        String groupCode = groupServiceImpl.getUserGroupCode(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_GROUP_CODE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_GROUP_CODE, groupCode));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteGroup(@RequestParam(value = "groupCode")String groupCode){

        String res = groupServiceImpl.deleteGroup(groupCode);


        return ResponseEntity
                .status(ResponseCode.SUCCESS_DELETE_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_DELETE_GROUP, res));
    }

    @PutMapping("/update-time")
    public ResponseEntity<ResponseDTO> updateTime(@RequestBody UpdateTimeDTO updateTimeDTO){

        UpdateTimeDTO res = groupServiceImpl.updateTime(updateTimeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPDATE_TIME.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPDATE_TIME, res));
    }

    @GetMapping("/get-time")
    public ResponseEntity<ResponseDTO> getTime(@RequestParam(value = "groupCode") String groupCode){

        UpdateTimeDTO res = groupServiceImpl.getTime(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_TIME.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_TIME, res));
    }

}
