package com.example.narshaback.base.dto.group;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTimeDTO {
    @NotNull
    private Integer startTime; // 시작 시간

    @NotNull
    private Integer endTime; // 끝 시간

    @NotNull
    private String groupCode; // 그룹 코드
}
