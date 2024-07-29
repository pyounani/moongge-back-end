package com.narsha.moongge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.group.JoinGroupDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.dto.user.UpdateUserProfileDTO;
import com.narsha.moongge.base.dto.user.UserLoginDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
import com.narsha.moongge.base.projection.post.GetOneUserPost;
import com.narsha.moongge.base.projection.user.GetUser;
import com.narsha.moongge.base.projection.user.GetUserProfile;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.service.AmazonS3Service;
import com.narsha.moongge.service.PostService;
import com.narsha.moongge.service.UserService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AmazonS3Service amazonS3Service;

    @GetMapping("/")
    public String welcome(){
        return "home";
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserRegisterDTO userRegisterDTO){
        GetUserProfile res = userService.register(userRegisterDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_REGISTER.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_REGISTER, res));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO){
        GetUserProfile res = userService.login(userLoginDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_LOGIN.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_LOGIN, res));

    }

    @GetMapping("/check-userId")
    public ResponseEntity<ResponseDTO> checkUserId(@RequestParam(value="userId")String userId){
        Boolean res = userService.checkUserId(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_LOGIN.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UNIQUE_ID, res));
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> joinGroup(@RequestBody JoinGroupDTO joinGroupDTO){
        UserEntity res = userService.joinUser(joinGroupDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_JOIN_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_JOIN_GROUP, res));
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateProfile(@RequestParam(value="image", required = false) MultipartFile profileImage,
                                @RequestParam(value="content") String updateUserProfileDTO) throws IOException, ParseException, ParseException {
        // System.out.println(profileImage);

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


    @GetMapping("/detail")
    public ResponseEntity<ResponseDTO> getProfile(@RequestParam(value = "userId")String userId){
        Optional<UserEntity> res = userService.getProfile(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_PROFILE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_PROFILE, res));
    }

    @GetMapping("/badge-list")
    public ResponseEntity<ResponseDTO> getBadgeList(@RequestParam(value = "userId")String userId){
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

    /**
     * 유저가 속해있는 그룹 코드 가져오기 API
     */
    @GetMapping("/{userId}/group-code")
    public ResponseEntity<ResponseDTO> getUserGroupCode(@PathVariable @NotEmpty String userId) {
        String groupCode = userService.getUserGroupCode(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_GROUP_CODE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_GROUP_CODE, groupCode));
    }

}
