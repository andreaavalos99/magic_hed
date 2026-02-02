package com.magiched.store.service;

import com.magiched.store.domain.Product;
import com.magiched.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> listActive(String q) {
        if (q == null || q.isBlank()) return repo.findByActiveTrueOrderByIdDesc();
        return repo.findByActiveTrueAndNameContainingIgnoreCaseOrderByIdDesc(q.trim());
    }

    public Product getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
}