package com.narsha.moongge.base.dto.group;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTimeDTO {

    @NotNull(message = "startTime을 입력해주세요.")
    private LocalTime startTime; // 시작 시간

    @NotNull(message = "endTime을 입력해주세요.")
    private LocalTime endTime; // 끝 시간

    private String groupCode; // 그룹 코드

}