package com.narsha.moongge.repository;

import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.repository.queryDSL.GroupRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity,String>, GroupRepositoryCustom {

    Optional<GroupEntity> findByGroupCode(String groupCode);
    boolean existsByGroupCode(String groupCode);
}
