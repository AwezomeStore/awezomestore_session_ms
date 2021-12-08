package com.awezomestore.awezomestore_session_ms.service;

import com.awezomestore.awezomestore_session_ms.dto.AccessDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    AccessService accessService;

    @Override
    public AccessDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        return accessService.getByUsername(username);
    }
    
}
