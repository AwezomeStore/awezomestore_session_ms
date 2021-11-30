package com.awezomestore.awezomestore_session_ms.service;

import java.util.List;

import com.awezomestore.awezomestore_session_ms.dto.AccessDTO;

public interface AccessService {
    
    List<AccessDTO> getAll();

    Boolean create(AccessDTO access);

    Boolean update(String id, AccessDTO access);

    Boolean delete(String id);
}
