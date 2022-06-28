package com.example.simplecrudjava.model;

import lombok.Data;

@Data
public class UserModel {
    private Long id;
    private String username;
    private String email;
}
