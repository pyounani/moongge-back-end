package com.narsha.moongge.base.dto.user;

import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDTO {

    private String userId;
    private UserType userType;
    private String username;
    private String nickname;
    private String profileImage;
    private String birth;
    private String intro;
    private String badgeList;

    public static UserProfileDTO mapToUserProfileDTO(UserEntity user) {
        return UserProfileDTO.builder()
                .userId(user.getUserId())
                .userType(user.getUserType())
                .username(user.getUserName())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .birth(user.getBirth())
                .intro(user.getIntro())
                .badgeList(user.getBadgeList())
                .build();
    }
}
