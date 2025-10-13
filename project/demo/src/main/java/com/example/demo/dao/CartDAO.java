package com.example.demo.dao;
import com.example.demo.model.CartItem;
import java.util.List;
public interface CartDAO {
    List<CartItem> getCartItemsByUserId(int userId);
    void addToCart(int userId, int productId, int quantity);
    double getCartTotal(int userId);
}