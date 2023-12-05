package com.example.JWT.jwt.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("roles")
@Getter
@Setter
public class Roles {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

//    private String name;

    public Roles() {

    }

    public Roles(ERole name) {
        this.name = name;
    }
}