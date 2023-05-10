package com.Jabaklahbackend.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreditorRequest {
    private String name;
    private String description;
    private String image;
}
