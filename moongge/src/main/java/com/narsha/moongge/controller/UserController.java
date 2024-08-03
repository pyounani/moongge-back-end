package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.dto.user.*;
import com.narsha.moongge.service.AmazonS3Service;
import com.narsha.moongge.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController // JSON 형태의 결과값 반환
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AmazonS3Service amazonS3Service;

    /**
     * 회원가입 API
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        UserDTO res = userService.register(userRegisterDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_REGISTER.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_REGISTER, res));
    }

    /**
     * 아이디 중복여부 API
     */
    @GetMapping("/{userId}/check-userId")
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
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        UserDTO res = userService.login(userLoginDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_LOGIN.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_LOGIN, res));

    }

    /**
     * 유저 정보 업데이트 API
     */
    @PutMapping("/{userId}/update")
    public ResponseEntity<ResponseDTO> updateProfile(@PathVariable String userId,
                                                     @RequestParam("image") MultipartFile multipartFile,
                                                     @RequestPart(value="content") UpdateUserProfileDTO updateUserProfileDTO) {
        UserProfileDTO res = userService.updateProfile(userId, multipartFile, updateUserProfileDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPDATE_PROFILE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPDATE_PROFILE, res));
    }

    /**
     * 유저 정보 조회하기 API
     */
    @GetMapping("/{userId}")
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
    public ResponseEntity<ResponseDTO> updateCheckAchieve(@PathVariable String userId,
                                                          @PathVariable Integer achieveNum) {
        String res = userService.updateBadgeList(userId, achieveNum);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPDATE_BADGE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPDATE_BADGE_LIST, res));
    }
}
