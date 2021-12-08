package com.awezomestore.awezomestore_session_ms.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class TokenDTO{
    
    public TokenDTO(String token, String userId, ArrayList<RoleDTO> roles) {
        this.token = token;
        this.userId = userId;
        this.roles = roles;
    }

    public TokenDTO(String token) {
        this.token = token;
    }

    public TokenDTO(){}

    private String id;
    private String userId;
    private String token;
    private ArrayList<RoleDTO> roles;
}