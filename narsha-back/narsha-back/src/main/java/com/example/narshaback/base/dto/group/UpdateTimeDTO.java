package com.example.narshaback.base.dto.group;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTimeDTO {
    @NotNull
    private LocalDateTime startTime; // 시작 시간

    @NotNull
    private LocalDateTime endTime; // 끝 시간

    @NotNull
    private String groupCode; // 그룹 코드
}
