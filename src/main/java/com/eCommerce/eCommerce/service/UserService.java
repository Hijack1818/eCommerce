package com.eCommerce.eCommerce.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerce.exception.ProductException;
import com.eCommerce.eCommerce.exception.UserException;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.model.User;
import com.eCommerce.eCommerce.repository.ProductRepository;
import com.eCommerce.eCommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addProductToCart(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with id: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found or Out of Stock with id: " + productId));
        // Add product to user's cart
        user.getCart().add(product);
        userRepository.save(user);
    }

    public Set<Product> getUserCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with id: " + userId));

        return user.getCart();
    }

    public void placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with id: " + userId));
        //user cart gets clear
        user.getCart().clear();
        //
        //order logic
        //
        userRepository.save(user);
    }
    
    public User addUser(User user) {
        return userRepository.save(user);
    }

	public void removeProductFromCart(Long userId, Long productId) {
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with id: " + userId));
		
		Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Enter valid product id: " + productId));
		
		if(!user.getCart().contains(product)) {
			throw new ProductException("Product not found in user Cart with id: "+productId);
		}
        // remove product from user's cart
        user.getCart().remove(product);
        userRepository.save(user);
	}
	
}
