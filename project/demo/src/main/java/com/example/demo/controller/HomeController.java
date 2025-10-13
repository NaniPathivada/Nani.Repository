package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "home";
    }

    @PostMapping("/add-to-cart/{productId}")
    public String addToCart(@PathVariable int productId) {
        int userId = 1; // Replace with session user ID
        cartService.addToCart(userId, productId, 1);
        return "redirect:/home";
    }

   @GetMapping("/cart")
public String cart(Model model, HttpSession session) {
    // Ensure userId is always set
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        userId = 1; // fallback for testing
    }

    model.addAttribute("cartItems", cartService.getCartItemsByUserId(userId));
    model.addAttribute("total", cartService.getCartTotal(userId));
    return "cart";
}

    @GetMapping("/checkout")
    public String checkout(Model model) {
        int userId = 1; // Replace with session user ID
        model.addAttribute("total", cartService.getCartTotal(userId));

        // Arrival date: 5 days from now
        java.time.LocalDate arrivalDate = java.time.LocalDate.now().plusDays(5);
        model.addAttribute("arrivalDate", arrivalDate);
        return "checkout";
    }
}