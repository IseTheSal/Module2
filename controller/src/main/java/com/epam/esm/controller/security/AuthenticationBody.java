package com.epam.esm.controller.security;

public class AuthenticationBody {

    private final String login;
    private final String password;

    public AuthenticationBody(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
