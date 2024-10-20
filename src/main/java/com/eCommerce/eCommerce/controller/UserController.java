package com.eCommerce.eCommerce.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.eCommerce.eCommerce.exception.UserException;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.model.User;
import com.eCommerce.eCommerce.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
    private UserService userService;

	// Add a new user
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>("User created successfully.\n"+userService.addUser(user), HttpStatus.CREATED);
    }
	
    //Add a product
    @PostMapping("/{userId}/cart")
    public ResponseEntity<String> addProductToCart(@PathVariable Long userId, @RequestBody Long productId) {
        userService.addProductToCart(userId, productId);
        return new ResponseEntity<>("Product added to cart successfully.", HttpStatus.CREATED);
    }
    
    //Remove a product from cart
    @DeleteMapping("/{userId}/cart")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long userId, @RequestBody Long productId) {
        userService.removeProductFromCart(userId, productId);
        return new ResponseEntity<>("Product removed from cart successfully.", HttpStatus.CREATED);
    }


    // Retrieve the user's cart
    @GetMapping("/{userId}/cart")
    public ResponseEntity<Set<Product>> getUserCart(@PathVariable Long userId) {
        Set<Product> cartContents = userService.getUserCart(userId);
        return new ResponseEntity<>(cartContents, HttpStatus.OK);
    }

    // Place an order based on the user's cart
    @PostMapping("/{userId}/order")
    public ResponseEntity<String> placeOrder(@PathVariable Long userId){
    	userService.placeOrder(userId);
        return new ResponseEntity<>("Order placed successfully.", HttpStatus.CREATED);
    }
	
}
