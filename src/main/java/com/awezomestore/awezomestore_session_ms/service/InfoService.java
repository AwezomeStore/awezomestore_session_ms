package com.awezomestore.awezomestore_session_ms.service;

import java.util.List;

import com.awezomestore.awezomestore_session_ms.dto.InfoDTO;

public interface InfoService {
    
    List<InfoDTO> getAll();

    Boolean create(InfoDTO info);

    Boolean update(String id, InfoDTO info);

    Boolean delete(String id);
}
