package com.Jabaklahbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@MappedSuperclass
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String cin;
    protected String email;
    protected Date birthDate;
    protected String phone;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Role role;
    @Column(nullable = false, unique = true)
    protected String username;
    @JsonIgnore
    @Column(nullable = false)
    protected String password;
    @Column(columnDefinition = "tinyint(1) default 0")
    protected boolean isPasswordChanged = Boolean.FALSE;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    @PrePersist
    public void setCreationDateTime() {
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void setChangeDateTime() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
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
