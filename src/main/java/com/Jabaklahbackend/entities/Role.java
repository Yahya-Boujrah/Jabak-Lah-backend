package com.Jabaklahbackend.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.Jabaklahbackend.entities.Privilege.*;

@RequiredArgsConstructor
public enum Role {

    CLIENT(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,

                    AGENT_READ,

                    AGENT_WRITE,

                    CLIENT_READ,

                    CLIENT_WRITE
            )
    ),
    AGENT(
            Set.of(
                    AGENT_READ,

                    CLIENT_READ,

                    CLIENT_WRITE
            )
    );

    @Getter
    private final Set<Privilege> privileges;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPrivileges()
                .stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}