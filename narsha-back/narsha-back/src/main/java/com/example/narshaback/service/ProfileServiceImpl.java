package com.example.narshaback.service;

import com.example.narshaback.dto.UpdateUserProfileDTO;
import com.example.narshaback.entity.ProfileEntity;
import com.example.narshaback.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;

    @Override
    public ProfileEntity updateProfile(UpdateUserProfileDTO updateUserProfileDTO) {
        // 특정 프로필 객체 업데이트
        ProfileEntity profile = profileRepository.findByProfile(updateUserProfileDTO.getUserGroupId());
        profile.setProfileImage(updateUserProfileDTO.getProfileImage());
        profile.setBirth(updateUserProfileDTO.getBirth());
        profile.setIntro(updateUserProfileDTO.getIntro());
        profile.setNikname(updateUserProfileDTO.getNikname());

        return profile;
    }
}
