package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepo;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo repository;
    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }
}
