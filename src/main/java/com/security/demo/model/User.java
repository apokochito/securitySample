package com.security.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String _id;

    @NotNull(message = "firstname must not be null")
    private String username;

    @NotNull(message = "email must not be null")
    private String email;

    private String token;
}
