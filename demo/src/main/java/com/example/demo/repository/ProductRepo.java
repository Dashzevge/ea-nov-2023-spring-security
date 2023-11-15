package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends ListCrudRepository<Product, Integer> {
}
