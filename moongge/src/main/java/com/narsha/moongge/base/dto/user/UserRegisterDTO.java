package com.narsha.moongge.base.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotNull
    private String userId;
    @NotNull
    private String password;
    @NotNull
    private String userType;
    @NotNull
    private String name;

}