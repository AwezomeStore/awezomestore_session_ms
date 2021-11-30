package com.awezomestore.awezomestore_session_ms.service;

import java.util.List;

import com.awezomestore.awezomestore_session_ms.dto.CountryDTO;

public interface CountryService {
    
    List<CountryDTO> getAll();

    Boolean create(CountryDTO country);

    Boolean update(String id, CountryDTO country);

    Boolean delete(String id);
}
