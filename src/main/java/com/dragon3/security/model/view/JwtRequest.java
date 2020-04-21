package com.dragon3.security.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JwtRequest {
    private String username;
    private String password;

    public JwtRequest(){}

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}
