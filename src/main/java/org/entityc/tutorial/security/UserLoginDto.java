package org.entityc.tutorial.security;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserLoginDto {
    @Email
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
