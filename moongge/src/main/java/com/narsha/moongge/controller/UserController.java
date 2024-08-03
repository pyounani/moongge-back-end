package com.narsha.moongge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.dto.user.*;
import com.narsha.moongge.base.projection.user.GetUser;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.service.AmazonS3Service;
import com.narsha.moongge.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateProfile(@RequestParam(value="image", required = false) MultipartFile profileImage,
                                @RequestParam(value="content") String updateUserProfileDTO) throws IOException, ParseException, ParseException {

        // mapper
        ObjectMapper mapper = new ObjectMapper();
        UpdateUserProfileDTO mapperUpdateUserProfileDTO = mapper.readValue(updateUserProfileDTO, UpdateUserProfileDTO.class);

        // 이미지 등록
        if (profileImage != null && profileImage.getResource().contentLength() != 0){
            // parse
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(updateUserProfileDTO);
            String userId = object.get("userId").toString();

            // 예전 유저의 프로필 이미지 삭제
            amazonS3Service.deleteS3(userId);

            // 이미지 업로드
            String uploadFileUrl = amazonS3Service.uploadFileToS3(profileImage, "");

            // updateUserProfileDTO 객체에 프로필 정보 설정
            mapperUpdateUserProfileDTO.setProfileImage(uploadFileUrl);
        }

        // 정보 업데이트
        UserEntity res = userService.updateProfile(mapperUpdateUserProfileDTO);

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

    @PutMapping("/check-achieve")
    public ResponseEntity<ResponseDTO> updateCheckAchieve(@RequestParam(value="userId")String userId, @RequestParam(value="achieveNum")Integer achNum){
        String res = userService.updateBadgeList(userId, achNum);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPDATE_BADGE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPDATE_BADGE_LIST, res));
    }

    @GetMapping("/student-list")
    public ResponseEntity<ResponseDTO> getStudentList(@RequestParam(value = "groupCode")String groupCode, @RequestParam(value = "userId")String userId){
        List<GetUser> res = userService.getStudentList(groupCode, userId);
        JsonObject obj = new JsonObject();

        if(res == null) {
            obj.addProperty("message", "유저가 없습니다.");
            //return obj.toString();
        }
        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_USER_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_USER_LIST, res));

    }
}
