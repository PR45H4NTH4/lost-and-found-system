package com.uni.lostandfound.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final String name;
    private final Long id;
    private final String role;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String name, Long id, String role) {
        super(username, password, authorities);
        this.name = name;
        this.id = id;
        this.role = role;
    }

    public String getName() {
        return name;
    }
    
    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
