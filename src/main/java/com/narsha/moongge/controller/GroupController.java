package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.group.GroupDTO;
import com.narsha.moongge.base.dto.group.JoinGroupDTO;
import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.dto.user.UserProfileDTO;
import com.narsha.moongge.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
@Tag(name = "GroupController", description = "GroupController API")
public class GroupController {

    private final GroupService groupService;

    /**
     * 그룹 생성 API
     */
    @PostMapping
    @Operation(
            summary = "그룹 생성 성공했습니다.",
            description = "새로운 그룹을 생성하는 API",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "그룹 생성에 필요한 정보",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "그룹 생성 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디가 존재하지 않습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "403", description = "학생은 그룹을 생성할 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> createGroup(@Valid @RequestBody CreateGroupDTO createGroupDTO) {
        String res = groupService.createGroup(createGroupDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_GROUP, res));
    }

    /**
     * 유저가 속해있는 그룹 코드 가져오기 API
     */
    @GetMapping("/users/{userId}")
    @Operation(
            summary = "유저의 그룹 코드 조회",
            description = "특정 유저가 속해있는 그룹의 코드를 조회하는 API",
            parameters = @Parameter(name = "userId", description = "유저 ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "그룹 코드 가져오기 성공했습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
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
    @Operation(
            summary = "그룹 가입",
            description = "유저가 그룹에 가입하는 API",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "그룹 가입에 필요한 정보",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "그룹에 가입되었습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> joinGroup(@Valid @RequestBody JoinGroupDTO joinGroupDTO) {
        GroupDTO res = groupService.joinGroup(joinGroupDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_JOIN_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_JOIN_GROUP, res));
    }

    /**
     * 그룹 삭제하기 API
     */
    @DeleteMapping("/{groupCode}")
    @Operation(
            summary = "그룹 삭제",
            description = "특정 그룹을 삭제하는 API",
            parameters = @Parameter(name = "groupCode", description = "그룹 코드", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "그룹 삭제 성공했습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
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
    @Operation(
            summary = "그룹 시간 등록 또는 수정",
            description = "그룹의 시간을 등록하거나 수정하는 API",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "그룹 시간에 대한 정보",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            ),
            parameters = @Parameter(name = "groupCode", description = "그룹 코드", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "앱 사용시간 설정 성공했습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> updateTime(@PathVariable String groupCode,
                                                  @Valid @RequestBody UpdateTimeDTO updateTimeDTO) {
        UpdateTimeDTO res = groupService.updateTime(groupCode, updateTimeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPDATE_TIME.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPDATE_TIME, res));
    }

    /**
     * 설정한 그룹 시간 불러오기 API
     */
    @GetMapping("/{groupCode}/time")
    @Operation(
            summary = "그룹 시간 조회",
            description = "설정한 그룹의 시간을 조회하는 API",
            parameters = @Parameter(name = "groupCode", description = "그룹 코드", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "앱 사용시간 불러오기 성공했습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getTime(@PathVariable String groupCode) {
        UpdateTimeDTO res = groupService.getTime(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_TIME.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_TIME, res));
    }

    /**
     * 그룹의 유저 목록 가져오기(요청한 유저 제외) API
     */
    @GetMapping("/{groupCode}/users/{userId}/list")
    @Operation(
            summary = "그룹 유저 목록 조회",
            description = "그룹의 유저 목록을 가져오되 요청한 유저를 제외하는 API",
            parameters = {
                    @Parameter(name = "groupCode", description = "그룹 코드", required = true),
                    @Parameter(name = "userId", description = "요청한 유저의 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "그룹의 유저 목록을 성공적으로 가져왔습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "그룹을 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getStudentList(@PathVariable String groupCode,
                                                      @PathVariable String userId) {
        List<UserProfileDTO> res = groupService.getStudentList(groupCode, userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_USER_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_USER_LIST, res));

    }

}
