package com.Jabaklahbackend.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProspectRequest {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private String cin;
}
