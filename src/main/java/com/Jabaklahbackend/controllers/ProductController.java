package com.Jabaklahbackend.controllers;

import com.Jabaklahbackend.payloads.Response;
import com.Jabaklahbackend.repositories.ProductRepo;
import com.Jabaklahbackend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;



    @GetMapping("/{id}")
    ResponseEntity<Response> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(Map.of("product", productService.getProductById(id)))
                .message("product found")
                .build());
    }

    @GetMapping("/search/findByCategoryId/{id}")
    ResponseEntity<Response> getProductsByCategory(@PathVariable Long id){

        return ResponseEntity.ok(Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(Map.of("products", productService.getProductsByCategory(id)))
                .message("list of all products")
                .build());
    }

    @GetMapping("/getCategories")
    ResponseEntity<Response> getProductCategories(){
        return ResponseEntity.ok(Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(Map.of("pcategories", productService.getProductCategories()))
                .message("list of categories")
                .build());
    }


    @GetMapping("/search/{name}")
    ResponseEntity<Response> getProductsByName(@PathVariable String name){
        return ResponseEntity.ok(Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(Map.of("products", productService.getProductsByName(name)))
                .message("list of products by name")
                .build());
    }


}
