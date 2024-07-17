package com.narsha.moongge.base.projection.post;

import com.narsha.moongge.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface GetPostDetail {

    Integer getId();
    String getContent();
    String getImageArray();
    LocalDateTime getCreateAt();
    UserEntity getWriter();

}
