package com.App.StudentManagement.Request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
