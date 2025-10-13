package com.example.demo.service;

import com.example.demo.model.CartItem;
import java.util.List;

public interface CartService {
    List<CartItem> getCartItemsByUserId(int userId);

    void addToCart(int userId, int productId, int quantity);

    double getCartTotal(int userId);
}