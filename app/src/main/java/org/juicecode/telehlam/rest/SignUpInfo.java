package org.juicecode.telehlam.rest;

public class SignUpInfo {
    private final long id;
    private final String token;

    public SignUpInfo(long id, String token) {
        this.id = id;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
