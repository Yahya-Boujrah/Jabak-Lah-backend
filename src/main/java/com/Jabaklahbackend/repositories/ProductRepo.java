package com.Jabaklahbackend.repositories;


import com.Jabaklahbackend.entities.Product;
import com.Jabaklahbackend.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<List<Product>> findByCategory(ProductCategory category);

    Optional<List<Product>> findByNameContaining(String name);
}