package com.example.JWT.jwt.model.response;

import com.example.JWT.jwt.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInResponse {
    private String token;
    private String type = "Bearer";
    private User user;

    public LogInResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
