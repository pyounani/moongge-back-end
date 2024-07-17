package com.narsha.moongge.base.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듦
public class UserRegisterDTO {

    @NotNull
    private String userId;
    @NotNull
    private String password;
    @NotNull
    private String userType;
    @NotNull
    private String name;

}