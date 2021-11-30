package com.awezomestore.awezomestore_session_ms.dto;

import lombok.Data;

@Data
public class AccessDTO{
    
    private String id;
    private String userId;
    private String username;
    private String password;
    private Integer level;
    private String group;
}