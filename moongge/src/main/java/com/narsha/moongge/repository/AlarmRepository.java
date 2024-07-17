package com.narsha.moongge.repository;

import com.narsha.moongge.entity.AlarmEntity;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<AlarmEntity, Integer> {

    Optional<AlarmEntity> deleteByLikeId(LikeEntity likeId);

    Optional<AlarmEntity> deleteByGroupCode(GroupEntity groupCode);

}
