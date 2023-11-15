package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    @GetMapping("")
    public List<Product> getProduct() {
        return service.findAll();
    }
}
