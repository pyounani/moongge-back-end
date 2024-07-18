package com.narsha.moongge.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinGroupDTO {

    @NotNull
    private String userId; // 유저 id

    @NotNull
    private String groupCode; // 그룹 코드
}
