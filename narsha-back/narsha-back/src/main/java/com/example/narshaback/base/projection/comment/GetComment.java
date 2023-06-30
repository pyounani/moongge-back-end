package com.example.narshaback.base.projection.comment;

import java.time.LocalDateTime;

public interface GetComment {
    String getContent();
    LocalDateTime getCreateAt();
}
