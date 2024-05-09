package com.app.quest.event;

import org.springframework.context.ApplicationEvent;

public class UserCreateEvent extends ApplicationEvent {
    private String email;

    public UserCreateEvent(Object source, String email) {
        super(source);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
