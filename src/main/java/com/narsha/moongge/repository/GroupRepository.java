package com.narsha.moongge.repository;

import com.narsha.moongge.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity,String> {

    Optional<GroupEntity> findByGroupCode(String groupCode);
    boolean existsByGroupCode(String groupCode);
}
