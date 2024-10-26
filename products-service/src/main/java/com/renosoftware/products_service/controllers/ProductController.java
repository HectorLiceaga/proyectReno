package com.renosoftware.products_service.controllers;

import com.renosoftware.products_service.model.dtos.ProductRequest;
import com.renosoftware.products_service.model.dtos.ProductResponse;
import com.renosoftware.products_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> add(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.created(null).body(this.productService.add(productRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAll() {
        return this.productService.findAll();
    }
}
