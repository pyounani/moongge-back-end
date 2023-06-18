package com.example.narshaback.service;

import com.example.narshaback.dto.profile.UpdateUserProfileDTO;
import com.example.narshaback.entity.ProfileEntity;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    ProfileEntity updateProfile(UpdateUserProfileDTO updateUserProfileDTO);

    Optional<ProfileEntity> getProfile(Integer ProfileId);

    String getBadgeList(Integer profileId);

    String updateBadgeList(Integer profileId, Integer achNum);
}
