package com.narsha.moongge.base.dto.group;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinGroupDTO {

    @NotNull
    private String userId; // 유저 id

    @NotNull
    private String groupCode; // 그룹 코드
}
