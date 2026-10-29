package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // Allows frontend to communicate smoothly
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // 1. Get all products (with optional search filter)
    // Example: GET /api/products or GET /api/products?search=mouse
    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return productRepository.findByName(search, search);
        }
        return productRepository.findAll();
    }

    // 2. Get a single product by ID
    // Example: GET /api/products/1
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}