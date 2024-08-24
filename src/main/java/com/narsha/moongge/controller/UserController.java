package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.dto.user.*;
import com.narsha.moongge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController // JSON 형태의 결과값 반환
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 API
     */
    @PostMapping("/register")
    @Operation(
            summary = "유저 회원가입",
            description = "새로운 유저 회원가입 할 때 사용하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입을 성공했습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "중복된 아이디가 있습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        UserDTO res = userService.register(userRegisterDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_REGISTER.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_REGISTER, res));
    }

    /**
     * 아이디 중복여부 API
     */
    @GetMapping("/{userId}/check-userId")
    @Operation(
            summary = "아이디 중복 확인",
            description = "주어진 유저 ID가 중복되는지 확인하는 API",
            parameters = @Parameter(name = "userId", description = "검사할 유저 ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "아이디 중복 확인 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> checkUserId(@PathVariable String userId) {
        Boolean res = userService.checkUserId(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CHECK_UNIQUE_ID.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CHECK_UNIQUE_ID, res));
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "유저 로그인 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디가 존재하지 않습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "비밀번호가 올바르지 않습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        UserDTO res = userService.login(userLoginDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_LOGIN.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_LOGIN, res));
    }

    /**
     * 유저 정보 업데이트 API
     */
    @PatchMapping("/{userId}/update")
    @Operation(
            summary = "유저 정보 업데이트",
            description = "유저의 프로필 정보를 업데이트하는 API",
            parameters = {
                    @Parameter(name = "userId", description = "업데이트할 유저 ID", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "업데이트할 유저 정보", required = true, content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "multipart/form-data")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 정보 업데이트 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> updateProfile(@PathVariable String userId,
                                                     @RequestParam("image") MultipartFile multipartFile,
                                                     @Valid @RequestPart(value="content") UpdateUserRequestDTO updateUserRequestDTO) {
        UserProfileDTO res = userService.updateProfile(userId, multipartFile, updateUserRequestDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPDATE_PROFILE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPDATE_PROFILE, res));
    }

    /**
     * 유저 정보 조회하기 API
     */
    @GetMapping("/{userId}")
    @Operation(
            summary = "유저 정보 조회",
            description = "주어진 유저 ID의 프로필 정보를 조회하는 API",
            parameters = @Parameter(name = "userId", description = "조회할 유저 ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getProfile(@PathVariable String userId) {
        UserProfileDTO res = userService.getProfile(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_PROFILE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_PROFILE, res));
    }

    /**
     * 뱃지 리스트 조회하기 API
     */
    @GetMapping("/{userId}/badge-list")
    @Operation(
            summary = "뱃지 리스트 조회",
            description = "주어진 유저 ID의 뱃지 리스트를 조회하는 API",
            parameters = @Parameter(name = "userId", description = "조회할 유저 ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "뱃지 리스트 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getBadgeList(@PathVariable String userId) {
        String res = userService.getBadgeList(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_BADGE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_BADGE_LIST, res));
    }

    /**
     * 특정 업적 달성 API(뱃지 리스트 업데이트 API)
     */
    @PutMapping("/{userId}/badge-list/{achieveNum}")
    @Operation(
            summary = "업적 달성",
            description = "특정 업적을 달성하여 뱃지 리스트를 업데이트하는 API",
            parameters = {
                    @Parameter(name = "userId", description = "업적을 달성할 유저 ID", required = true),
                    @Parameter(name = "achieveNum", description = "업적 번호", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "업적 달성 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> updateCheckAchieve(@PathVariable String userId,
                                                          @PathVariable Integer achieveNum) {
        String res = userService.updateBadgeList(userId, achieveNum);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPDATE_BADGE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPDATE_BADGE_LIST, res));
    }
}
