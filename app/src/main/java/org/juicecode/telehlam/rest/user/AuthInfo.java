package org.juicecode.telehlam.rest.user;

public class AuthInfo {
    private final long id;
    private final String token;

    public AuthInfo(long id, String token) {
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
