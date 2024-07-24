package com.narsha.moongge.base.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTimeDTO {

    private LocalTime startTime; // 시작 시간

    private LocalTime endTime; // 끝 시간

    private String groupCode; // 그룹 코드

}