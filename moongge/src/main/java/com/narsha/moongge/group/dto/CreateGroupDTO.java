package com.narsha.moongge.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupDTO {

    @NotNull
    private String groupName;

    @NotNull
    private String userId;

    @NotNull
    private String school;

    @NotNull
    private Integer grade;

    @NotNull
    private Integer group_class;
}
