package com.example.narshaback.service;

import com.example.narshaback.dto.profile.UpdateUserProfileDTO;
import com.example.narshaback.entity.ProfileEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.repository.ProfileRepository;
import com.example.narshaback.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;

    private final UserGroupRepository userGroupRepository;

    @Override
    public ProfileEntity updateProfile(UpdateUserProfileDTO updateUserProfileDTO) {
        // 유저-그룹 객체 가져오기
        Optional<User_Group> findUserGroup = userGroupRepository.findById(updateUserProfileDTO.getUserGroupId());

        // 특정 프로필 객체 업데이트
        ProfileEntity profile = profileRepository.findByUserGroup(findUserGroup.get());
        profile.setProfileImage(updateUserProfileDTO.getProfileImage());
        profile.setBirth(updateUserProfileDTO.getBirth());
        profile.setIntro(updateUserProfileDTO.getIntro());
        profile.setNikname(updateUserProfileDTO.getNikname());

        profileRepository.save(profile);

        return profile;
    }
}
