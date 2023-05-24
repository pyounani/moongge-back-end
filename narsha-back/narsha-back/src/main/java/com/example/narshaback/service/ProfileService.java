package com.example.narshaback.service;

import com.example.narshaback.dto.profile.UpdateUserProfileDTO;
import com.example.narshaback.entity.ProfileEntity;

public interface ProfileService {
    ProfileEntity updateProfile(UpdateUserProfileDTO updateUserProfileDTO);
}
