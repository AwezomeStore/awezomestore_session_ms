package com.awezomestore.awezomestore_session_ms.service;

import java.util.List;

import com.awezomestore.awezomestore_session_ms.dto.LanguageDTO;

public interface LanguageService {
    
    List<LanguageDTO> getAll();

    Boolean create(LanguageDTO language);

    Boolean update(String id, LanguageDTO language);

    Boolean delete(String id);
}
