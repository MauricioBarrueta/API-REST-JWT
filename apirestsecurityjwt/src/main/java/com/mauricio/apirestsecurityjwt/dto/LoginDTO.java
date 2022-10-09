package com.mauricio.apirestsecurityjwt.dto;

public class LoginDTO {

    private String usernameOrEmail;
    private String password;

    public LoginDTO() { }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
