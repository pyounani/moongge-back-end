package com.narsha.moongge.base.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotEmpty(message = "userId를 입력하지 않았습니다.")
    private String userId;
    @NotEmpty(message = "password를 입력하지 않았습니다.")
    private String password;
    @NotEmpty(message = "userType을 입력하지 않았습니다.")
    private String userType;
    @NotEmpty(message = "name을 입력하지 않았습니다.")
    private String name;

}