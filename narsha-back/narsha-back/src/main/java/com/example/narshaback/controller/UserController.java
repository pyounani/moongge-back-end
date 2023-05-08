package com.example.narshaback.controller;

import com.example.narshaback.dto.UserRegisterDTO;
import com.example.narshaback.service.UserService;
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
    public String register(@RequestBody UserRegisterDTO userDTO){
        System.out.print(userDTO);
        String userId = userService.register(userDTO);
        return "회원가입이 완료되었습니다. + ${userId}";
    }


}
