package com.Jabaklahbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Privilege {



    ADMIN_READ("admin:read"),

    AGENT_READ("agent:read"),

    AGENT_WRITE("agent:write"),

    CLIENT_READ("client:read"),

    CLIENT_WRITE("client:write")



    ;

    @Getter
    private final String name;


}