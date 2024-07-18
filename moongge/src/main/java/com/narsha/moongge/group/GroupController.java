package com.narsha.moongge.group;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.group.dto.CreateGroupDTO;
import com.narsha.moongge.group.dto.UpdateTimeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;

    /**
     * 그룹 생성 API
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createGroup(@Valid @RequestBody CreateGroupDTO createGroupDTO){
        String userId = groupService.createGroup(createGroupDTO);

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
