package com.Jabaklahbackend.controllers;

import com.Jabaklahbackend.entities.Product;
import com.Jabaklahbackend.entities.ProductCategory;
import com.Jabaklahbackend.payloads.ProductRequest;
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
    @GetMapping("/products")
    ResponseEntity<Response> getProducts(){
        return ResponseEntity.ok(Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(Map.of("products", productService.getProducts()))
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

    @PostMapping("/saveProduct")
    ResponseEntity<Response> saveProduct(@RequestBody ProductRequest product){
        return ResponseEntity.ok(Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .data(Map.of("product", productService.addProduct(product)))
                .message("product saved")
                .build());
    }
    @PutMapping("/updateProduct/{id}")
    ResponseEntity<Response> updateProduct( @PathVariable Long id ,@RequestBody ProductRequest product){
        return ResponseEntity.ok(Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(Map.of("product", productService.updateProduct(product, id)))
                .message("product saved")
                .build());
    }

    @PostMapping("/saveCategory")
    ResponseEntity<Response> saveCategory(@RequestBody ProductCategory category){
        return ResponseEntity.ok(Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .data(Map.of("category", productService.addProductCategory(category)))
                .message("category saved")
                .build());
    }
    @DeleteMapping ("/deleteProduct/{id}")
    ResponseEntity<Response> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok(Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(Map.of("deleted", productService.deleteProduct(id)))
                .message("product deleted")
                .build());
    }
    @DeleteMapping ("/deleteCategory/{id}")
    ResponseEntity<Response> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok(Response.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(Map.of("deleted", productService.deleteCategory(id)))
                .message("Category deleted")
                .build());
    }

}
