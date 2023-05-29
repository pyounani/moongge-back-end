package com.example.narshaback.projection.comment;

import java.time.LocalDateTime;

public interface GetComment {
    String getContent();

    LocalDateTime getCreateAt();
}
