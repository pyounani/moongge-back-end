package com.example.narshaback.event;

import com.example.narshaback.entity.LikeEntity;
import com.example.narshaback.entity.NoticeEntity;
import org.springframework.context.ApplicationEvent;

public class LikeCreatedEvent extends ApplicationEvent{

    private LikeEntity like;

    public LikeCreatedEvent(Object source, LikeEntity like) {
        super(source);
        this.like = like;
    }

    public LikeEntity getLike() {
        return like;
    }


}
