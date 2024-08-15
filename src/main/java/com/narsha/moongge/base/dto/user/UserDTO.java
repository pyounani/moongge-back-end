package com.narsha.moongge.base.dto.user;

import com.narsha.moongge.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {

    private String userId;
    private String userType;
    private String username;

    public static UserDTO mapToUserDTO(UserEntity user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .userType(user.getUserType())
                .username(user.getUserName())
                .build();
    }
}
