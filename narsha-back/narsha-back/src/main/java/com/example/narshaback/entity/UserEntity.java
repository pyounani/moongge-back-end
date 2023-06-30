package com.example.narshaback.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder // DTO -> Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(nullable = false, length=100)
    private String userId; // 유저 id

    @Column(nullable = false, length=100)
    private String password; // 패스워드

    @Column(nullable = false, length=20)
    private String userType; // 유저 타입: teacher | student

    @Column(nullable = false, length=20)
    private String userName; // 이름
}
