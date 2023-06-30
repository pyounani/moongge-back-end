package com.example.narshaback.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User_Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userGroupId; // 유저-그룹 id

    @ManyToOne
    private UserEntity userId; // 유저 id

    @ManyToOne
    private GroupEntity groupCode; // 그룹 id
}