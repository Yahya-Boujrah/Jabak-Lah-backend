package com.Jabaklahbackend.payloads;

import com.Jabaklahbackend.entities.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductRequest {
    private Long categoryId;

    private String sku;

    private String name;

    private String description;

    private BigDecimal unitPrice;
    private int unitsInStock;
    private String imageUrl;
}
