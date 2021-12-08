package com.awezomestore.awezomestore_session_ms.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class AccessDTO implements UserDetails {

    private String id;
    private String userId;
    private String username;
    private String password;
    private Long level;
    private ArrayList<RoleDTO> authorities;

    public AccessDTO(String userId, String username, String password, Long level,
            ArrayList<RoleDTO> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.level = level;
        this.authorities = authorities;
    }

    public AccessDTO() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = this.authorities.stream().map(rol -> new SimpleGrantedAuthority(rol
                .getRoleName().name())).collect(Collectors.toList());
        return auths;
    }

    public void setAuthorities(
            ArrayList<RoleDTO> authorities) {
        this.authorities = authorities;
    }

    public void setRoles(ArrayList<RoleDTO> roles){
        this.authorities = roles;
    }

    public ArrayList<RoleDTO> getRoles(){
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}