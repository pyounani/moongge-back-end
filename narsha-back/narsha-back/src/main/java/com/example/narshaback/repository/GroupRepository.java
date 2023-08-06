package com.example.narshaback.repository;

import com.example.narshaback.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity,String> {
    Optional<GroupEntity> findByGroupCode(String groupCode);

    Optional<GroupEntity> deleteByGroupCode(String groupCode);

}
