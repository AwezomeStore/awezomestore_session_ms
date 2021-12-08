package com.awezomestore.awezomestore_session_ms.service;

import java.util.List;

import com.awezomestore.awezomestore_session_ms.dto.RoleDTO;
import com.awezomestore.awezomestore_session_ms.enums.RoleName;

public interface RoleService {
    
    RoleDTO getById(String id);

    List<RoleDTO> getAll();

    RoleDTO getByRoleName(RoleName roleName);

    Boolean create(RoleDTO role);

    Boolean update(String id, RoleDTO role);

    Boolean delete(String id);
}
