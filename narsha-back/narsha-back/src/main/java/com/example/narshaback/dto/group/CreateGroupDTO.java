package com.example.narshaback.dto.group;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
