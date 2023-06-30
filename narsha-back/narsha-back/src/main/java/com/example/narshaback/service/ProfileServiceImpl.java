package com.example.narshaback.service;

import com.example.narshaback.dto.profile.UpdateUserProfileDTO;
import com.example.narshaback.entity.ProfileEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.repository.ProfileRepository;
import com.example.narshaback.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;

    private final UserGroupRepository userGroupRepository;

    @Override
    public ProfileEntity updateProfile(UpdateUserProfileDTO updateUserProfileDTO) {
        // 유저-그룹 객체 가져오기
        Optional<User_Group> findUserGroup = userGroupRepository.findByUserGroupId(updateUserProfileDTO.getUserGroupId());

        // 특정 프로필 객체 업데이트
        ProfileEntity profile = profileRepository.findByUserGroupId(findUserGroup.get());
        profile.setProfileImage(updateUserProfileDTO.getProfileImage());
        profile.setBirth(updateUserProfileDTO.getBirth());
        profile.setIntro(updateUserProfileDTO.getIntro());
        profile.setNikname(updateUserProfileDTO.getNikname());

        profileRepository.save(profile);

        return profile;
    }

    @Override
    public Optional<ProfileEntity> getProfile(Integer ProfileId) {
        Optional<ProfileEntity> profile = profileRepository.findById(ProfileId);

        return profile;
    }

    @Override
    public String getBadgeList(Integer profileId) {
        Optional<ProfileEntity> profile = profileRepository.findByProfileId(profileId);
        String badgeList = profile.get().getBadgeList();

        return badgeList;
    }

    @Override
    public String updateBadgeList(Integer profileId, Integer achNum) {
        Optional<ProfileEntity> profile = profileRepository.findByProfileId(profileId);
        ProfileEntity userProfile = profile.get();

        // parse object
        String badgeListStr = userProfile.getBadgeList();
        JSONParser parser = new JSONParser(badgeListStr);

        try {
            // string parse & convert array
            List<Boolean> badgeList = (List<Boolean>) parser.parse();
            badgeList.set(achNum - 1, true);

            // update
            userProfile.setBadgeList(badgeList.toString());
            profileRepository.save(userProfile);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return userProfile.getBadgeList();
    }
}
