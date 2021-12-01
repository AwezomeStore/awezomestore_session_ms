package com.awezomestore.awezomestore_session_ms.service;

import java.util.List;

import com.awezomestore.awezomestore_session_ms.dto.CurrencyDTO;

public interface CurrencyService {
    
    List<CurrencyDTO> getAll();

    Boolean create(CurrencyDTO country);

    Boolean update(String id, CurrencyDTO country);

    Boolean delete(String id);
}
