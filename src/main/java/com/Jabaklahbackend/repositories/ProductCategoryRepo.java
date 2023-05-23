package com.Jabaklahbackend.repositories;

import com.Jabaklahbackend.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {
}
