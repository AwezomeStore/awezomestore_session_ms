package com.awezomestore.awezomestore_session_ms.dto;

import lombok.Data;

@Data
public class TokenDTO{
    
    private String id;
    private String user_id;
    private String token;
    private Boolean valid;
}