package com.narsha.moongge.base.dto.group;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinGroupDTO {

    @NotEmpty(message = "userId를 입력하세요.")
    private String userId; // 유저 id

    @NotEmpty(message = "groupCode를 입력하세요.")
    private String groupCode; // 그룹 코드
}
