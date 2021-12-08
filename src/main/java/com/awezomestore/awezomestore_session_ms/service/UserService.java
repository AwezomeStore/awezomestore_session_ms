package com.awezomestore.awezomestore_session_ms.service;

import java.util.List;

import com.awezomestore.awezomestore_session_ms.dto.UserDTO;

public interface UserService {

    UserDTO getById(String id);
    
    List<UserDTO> getAll();

    String create(UserDTO user);

    Boolean update(String id, UserDTO user);

    Boolean delete(String id);
}
