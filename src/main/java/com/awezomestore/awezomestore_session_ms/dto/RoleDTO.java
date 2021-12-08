package com.awezomestore.awezomestore_session_ms.dto;

import com.awezomestore.awezomestore_session_ms.enums.RoleName;

import lombok.Data;

@Data
public class RoleDTO{
    
    private String id;
    private RoleName roleName;
}