package com.example.narshaback.controller;

import com.example.narshaback.dto.user.UserLoginDTO;
import com.example.narshaback.dto.user.UserRegisterDTO;
import com.example.narshaback.service.UserService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String welcome(){
        return "home";
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRegisterDTO userRegisterDTO){
        String res = userService.register(userRegisterDTO);
        JsonObject obj = new JsonObject();
        obj.addProperty("res", res);

        if (res == null) obj.addProperty("message", "중복된 아이디 존재, 회원가입 실패");
        else obj.addProperty("message", "회원가입 완료");

        return obj.toString();
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO userLoginDTO){
        Integer res = userService.login(userLoginDTO);
        JsonObject obj = new JsonObject();
        obj.addProperty("res", res);

        switch(res){
            case 1: obj.addProperty("message", "아이디가 존재하지 않습니다."); break;
            case 2: obj.addProperty("message", "비밀번호가 틀렸습니다."); break;
            case 3: obj.addProperty("message", "로그인 성공"); break;
        }

        return obj.toString();

    }

}
