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
    private UserEntity user;

    @NotNull
    private GroupEntity group;
}
