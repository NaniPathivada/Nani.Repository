package com.example.demo.dao;

import com.example.demo.model.Product;
import java.util.List;

public interface ProductDAO {
    List<Product> getAllProducts();

    Product getProductById(int id);
}