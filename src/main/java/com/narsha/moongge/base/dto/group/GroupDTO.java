package com.narsha.moongge.base.dto.group;

import com.narsha.moongge.base.dto.user.UserDTO;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupDTO {
    private String userId;
    private UserType userType;
    private String username;
    private String groupCode;
    private String groupName;
    private String school;
    private Integer grade;
    private Integer groupClass;

    public static GroupDTO mapToGroupDTO(UserEntity user) {
        return GroupDTO.builder()
                .userId(user.getUserId())
                .userType(user.getUserType())
                .username(user.getUserName())
                .groupCode(user.getGroup().getGroupCode())
                .groupName(user.getGroup().getGroupName())
                .school(user.getGroup().getSchool())
                .grade(user.getGroup().getGrade())
                .groupClass(user.getGroup().getGroupClass())
                .build();
    }
}
