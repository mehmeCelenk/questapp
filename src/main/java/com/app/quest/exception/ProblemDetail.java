package com.app.quest.exception;

public class ProblemDetail {
    String message;

    public ProblemDetail() {
    }

    public ProblemDetail(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
