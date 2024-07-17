package com.narsha.moongge.base.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @NotNull
    public String userId;
    @NotNull
    public String password;
}