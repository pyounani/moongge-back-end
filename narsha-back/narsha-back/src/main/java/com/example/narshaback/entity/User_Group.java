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
    private Integer id; // 유저-그룹 id

    @ManyToOne
    private UserEntity user; // 유저 id

    @ManyToOne
    private GroupEntity group; // 그룹 id
}