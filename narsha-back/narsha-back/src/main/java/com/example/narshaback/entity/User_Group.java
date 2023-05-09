package com.example.narshaback.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
public class User_Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 유저-그룹 id

    @ManyToOne
    private UserEntity user; // 유저 id

    @ManyToOne
    private GroupEntity group; // 그룹 id
}