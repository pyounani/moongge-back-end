package com.example.narshaback.dto;

import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinGroupDTO {
    @NotNull
    private String userId; // 유저 id

    @NotNull
    private String groupCode; // 그룹 코드
}
