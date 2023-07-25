package com.example.narshaback.repository;
import com.example.narshaback.base.projection.user.GetUser;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

// JpaRepository<Class Type, PK Type>
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> deleteByGroupCode(GroupEntity groupCode);
    List<GetUser> findByGroupCode(GroupEntity GroupId);

//    Optional<UserEntity> findByGroupCode(String groupCode);
}
