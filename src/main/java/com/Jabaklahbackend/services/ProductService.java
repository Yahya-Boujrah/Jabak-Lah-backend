package com.Jabaklahbackend.services;

import com.Jabaklahbackend.entities.Product;
import com.Jabaklahbackend.entities.ProductCategory;
import com.Jabaklahbackend.payloads.ProductRequest;
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

    public List<Product> getProducts(){
        return productRepo.findAll();
    }

    public Product addProduct(ProductRequest request){
        Product product = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .imageUrl(request.getImageUrl())
                .description(request.getDescription())
                .unitsInStock(request.getUnitsInStock())
                .unitPrice(request.getUnitPrice())
                .category(categoryRepo.findById(request.getCategoryId()).orElseThrow())
                .build();
        return productRepo.save(product);

    }
    public Product updateProduct(ProductRequest request, Long id){
        System.out.println(request);

        Product product = productRepo.findById(id).orElseThrow();

        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setImageUrl(request.getImageUrl());
        product.setDescription(request.getDescription());
        product.setUnitPrice(request.getUnitPrice());
        product.setUnitsInStock(request.getUnitsInStock());
        product.setCategory(categoryRepo.findById(request.getCategoryId()).orElseThrow());
        return productRepo.save(product);
    }
    public ProductCategory addProductCategory(ProductCategory category){
        return categoryRepo.save(category);
    }

    public Boolean deleteProduct(Long id){
        Product product = productRepo.findById(id).orElseThrow();
        productRepo.delete(product);
        return Boolean.TRUE;
    }
    public Boolean deleteCategory(Long id){
        ProductCategory category = categoryRepo.findById(id).orElseThrow();
        categoryRepo.delete(category);
        return Boolean.TRUE;
    }
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
