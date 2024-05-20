package com.rosorio.customer.persistence.entities;

import com.rosorio.customer.config.security.Authority;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class User implements UserDetails {
    private String id;
    private String fullName;
    private String email;
    private String password;
    private Date createdAt;
    private Date updatedAt;
    private List<String> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities != null && !authorities.isEmpty()) {
            return authorities.stream()
                    .map(authority -> (GrantedAuthority) () -> authority)
                    .toList();
        }
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
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
