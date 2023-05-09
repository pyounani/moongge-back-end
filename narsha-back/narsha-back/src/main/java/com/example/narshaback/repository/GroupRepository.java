package com.example.narshaback.repository;

import com.example.narshaback.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity,String> {
    GroupEntity findByGroupCode(String string);
}
