package com.example.narshaback.controller;

import com.example.narshaback.api.ResponseCode;
import com.example.narshaback.dto.response.ResponseDTO;
import com.example.narshaback.dto.user.UserLoginDTO;
import com.example.narshaback.dto.user.UserRegisterDTO;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.service.UserService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String welcome(){
        return "home";
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserRegisterDTO userRegisterDTO){
        String res = userService.register(userRegisterDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_REGISTER.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_REGISTER, res));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO){
        UserEntity res = userService.login(userLoginDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_LOGIN.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_LOGIN, res));

    }

    @GetMapping("/check-userId")
    public String checkUserId(@RequestParam(value="userId")String userId){
        Boolean res = userService.checkUserId(userId);
        JsonObject obj =  new JsonObject();
        obj.addProperty("res", res);

        if (res) obj.addProperty("message", "사용 가능한 아이디입니다.");
        else obj.addProperty("message", "이미 존재하는 아이디입니다.");

        return obj.toString();
    }

}
