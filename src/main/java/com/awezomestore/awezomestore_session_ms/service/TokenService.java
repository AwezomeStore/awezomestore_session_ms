package com.awezomestore.awezomestore_session_ms.service;

import java.util.List;

import com.awezomestore.awezomestore_session_ms.dto.TokenDTO;

public interface TokenService {
    
    List<TokenDTO> getAll();

    Boolean create(TokenDTO token);

    Boolean update(String id, TokenDTO token);

    Boolean delete(String id);
}
