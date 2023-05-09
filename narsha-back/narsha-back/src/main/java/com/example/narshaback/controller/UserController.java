package com.example.narshaback.controller;

import com.example.narshaback.dto.UserLoginDTO;
import com.example.narshaback.dto.UserRegisterDTO;
import com.example.narshaback.service.UserService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.print(userRegisterDTO);
        String userId = userService.register(userRegisterDTO);
        return "회원가입이 완료되었습니다. + ${userId}";
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
