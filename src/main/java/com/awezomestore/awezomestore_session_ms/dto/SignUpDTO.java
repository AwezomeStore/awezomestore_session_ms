package com.awezomestore.awezomestore_session_ms.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class SignUpDTO{
    
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private ArrayList<String> roles;
}