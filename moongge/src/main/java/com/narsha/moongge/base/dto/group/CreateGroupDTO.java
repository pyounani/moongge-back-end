package com.narsha.moongge.base.dto.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupDTO {

    @NotBlank(message = "groupName을 입력해주세요.")
    private String groupName;

    @NotBlank(message = "userId을 입력해주세요.")
    private String userId;

    @NotBlank(message = "school을 입력해주세요.")
    private String school;

    @NotNull(message = "grade을 입력해주세요.")
    private Integer grade;

    @NotNull(message = "group_class을 입력해주세요.")
    private Integer group_class;
}
