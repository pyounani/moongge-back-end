package com.narsha.moongge.base.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @NotEmpty(message = "userId를 입력하지 않았습니다.")
    public String userId;
    @NotEmpty(message = "password를 입력하지 않았습니다.")
    public String password;
}