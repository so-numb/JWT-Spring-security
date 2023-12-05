package com.example.JWT.jwt.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInRequest {
    private String username;

    private String password;
}
