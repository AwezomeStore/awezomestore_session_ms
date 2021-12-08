package com.awezomestore.awezomestore_session_ms.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.awezomestore.awezomestore_session_ms.dto.AccessDTO;
import com.awezomestore.awezomestore_session_ms.dto.RoleDTO;
import com.awezomestore.awezomestore_session_ms.dto.SignUpDTO;
import com.awezomestore.awezomestore_session_ms.dto.TokenDTO;
import com.awezomestore.awezomestore_session_ms.dto.UserDTO;
import com.awezomestore.awezomestore_session_ms.enums.RoleName;
import com.awezomestore.awezomestore_session_ms.security.jwt.JwtProvider;
import com.awezomestore.awezomestore_session_ms.service.AccessService;
import com.awezomestore.awezomestore_session_ms.service.RoleService;
import com.awezomestore.awezomestore_session_ms.service.TokenService;
import com.awezomestore.awezomestore_session_ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/session")
public class SessionController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    AccessService accessService;

    @Autowired
    TokenService tokenService;

    @Autowired
    RoleService RoleService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestBody SignUpDTO signUpDTO) {
        if (signUpDTO.getFirstName() == null || signUpDTO.getLastName() == null || signUpDTO.getUsername() == null
                || signUpDTO.getPassword() == null) {
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
        if (accessService.getByUsername(signUpDTO.getUsername()) != null) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        UserDTO user = new UserDTO();
        user.setFirstName(signUpDTO.getFirstName());
        user.setLastName(signUpDTO.getLastName());
        String userId = userService.create(user);
        if (userId == null) {
            return new ResponseEntity<>("Cannot create the user in Firestore", HttpStatus.CONFLICT);
        }
        ArrayList<RoleDTO> roles = new ArrayList<RoleDTO>();
        if (signUpDTO.getRoles() != null) {
            if (signUpDTO.getRoles().contains("vendor")) {
                roles.add(RoleService.getByRoleName(RoleName.ROLE_VENDOR));
            }
            if (signUpDTO.getRoles().contains("buyer")) {
                roles.add(RoleService.getByRoleName(RoleName.ROLE_BUYER));
            }
            if (signUpDTO.getRoles().contains("support")) {
                roles.add(RoleService.getByRoleName(RoleName.ROLE_SUPPORT));
            }
        } else {
            roles.add(RoleService.getByRoleName(RoleName.ROLE_BUYER));
        }
        Long level = (long) 1;
        AccessDTO access = new AccessDTO();
        access.setUserId(userId);
        access.setUsername(signUpDTO.getUsername());
        access.setPassword(signUpDTO.getPassword());
        access.setLevel(level);
        access.setRoles(roles);
        return new ResponseEntity<>(accessService.create(access), HttpStatus.CREATED);
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<TokenDTO> signin(@RequestBody AccessDTO access) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(access.getUsername(), access.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            AccessDTO accessDTO = (AccessDTO) authentication.getPrincipal();
            TokenDTO token = new TokenDTO(jwt, accessDTO.getUserId(), accessDTO.getRoles());
            token.setId(tokenService.create(token));
            return new ResponseEntity<>(token, HttpStatus.CREATED);
        } catch (BadCredentialsException e) {
            TokenDTO token = new TokenDTO("Incorrect credentials");
            return new ResponseEntity<>(token, HttpStatus.BAD_REQUEST);
        }
    }

}
