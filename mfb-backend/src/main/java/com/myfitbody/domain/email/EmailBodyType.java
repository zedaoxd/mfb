package com.myfitbody.domain.email;

public enum EmailBodyType {
    VERIFY_EMAIL("VERIFY_EMAIL"),
    RESET_PASSWORD("RESET_PASSWORD");

    private final String body;

    EmailBodyType(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
