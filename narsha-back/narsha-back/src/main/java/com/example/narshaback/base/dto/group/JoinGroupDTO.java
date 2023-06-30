package com.example.narshaback.base.dto.group;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinGroupDTO {
    @NotNull
    private String userId; // 유저 id

    @NotNull
    private String groupCode; // 그룹 코드
}
