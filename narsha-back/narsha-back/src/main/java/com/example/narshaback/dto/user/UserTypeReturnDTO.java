package com.example.narshaback.dto.user;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeReturnDTO {
    @NotNull
    private String userId;

    @NotNull
    private String userType;
}
