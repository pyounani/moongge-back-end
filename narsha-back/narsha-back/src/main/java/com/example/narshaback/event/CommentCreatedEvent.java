package com.example.narshaback.event;

import com.example.narshaback.entity.CommentEntity;
import org.springframework.context.ApplicationEvent;

public class CommentCreatedEvent extends ApplicationEvent {

    private CommentEntity comment;

    public CommentCreatedEvent(Object source, CommentEntity comment) {
        super(source);
        this.comment = comment;
    }

    public CommentEntity getComment() {
        return comment;
    }
}

