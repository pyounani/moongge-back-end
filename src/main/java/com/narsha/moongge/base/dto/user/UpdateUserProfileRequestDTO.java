package com.narsha.moongge.base.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileRequestDTO {

    @NotEmpty(message = "userId를 입력하지 않았습니다.")
    private String userId; // 유저 id

    @NotEmpty(message = "birth를 입력하지 않았습니다.")
    private String birth; // 생일

    @NotEmpty(message = "nickname을 입력하지 않았습니다.")
    private String nickname; // 닉네임

    @NotEmpty(message = "intro 입력하지 않았습니다.")
    private String intro; // 소개
}
