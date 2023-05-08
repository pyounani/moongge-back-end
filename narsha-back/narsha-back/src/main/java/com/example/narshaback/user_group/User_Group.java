package com.example.narshaback.user_group;

import com.example.narshaback.group.GroupEntity;
import com.example.narshaback.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class User_Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 유저-그룹 id

    @ManyToOne
    private UserEntity user; // 유저 id

    @ManyToOne
    private GroupEntity group; // 그룹 id
}