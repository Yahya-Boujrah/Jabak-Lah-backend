package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Product;
import com.Jabaklahbackend.entities.ProductCategory;
import com.Jabaklahbackend.repositories.ProductCategoryRepo;
import com.Jabaklahbackend.repositories.ProductRepo;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {


    private final ProductRepo productRepo;

    private final ProductCategoryRepo categoryRepo;

    public List<Product> getProductsByCategory(Long id){
        ProductCategory category = categoryRepo.findById(id).orElseThrow();
        List<Product> products = productRepo.findByCategory(category).orElseThrow();
        return products;
    }

    public Product getProductById(Long id){
        return productRepo.findById(id).orElseThrow();
    }

    public List<ProductCategory> getProductCategories(){
        return categoryRepo.findAll();
    }


    public List<Product> getProductsByName(String name){
        return productRepo.findByNameContaining(name).orElseThrow();
    }

}
