package com.eCommerce.eCommerce.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.eCommerce.eCommerce.exception.UserException;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.model.User;
import com.eCommerce.eCommerce.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
    void testAddUser() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setPhoneNumber("1234567890");

        when(userService.addUser(any())).thenReturn(user);

        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\", \"phoneNumber\":\"1234567890\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("User created successfully.\n" + user));
    }

    @Test
    void testAddProductToCart() throws Exception {
        Long userId = 1L;
        Long productId = 1L;

        doNothing().when(userService).addProductToCart(userId, productId);

        mockMvc.perform(post("/api/user/1/cart", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productId.toString()))
                .andExpect(status().isCreated())
                .andExpect(content().string("Product added to cart successfully."));
    }
    
    @Test
    void testAddProductToCartUserNotFound() throws Exception {
        Long userId = 1L;
        Long productId = 1L;

        doThrow(new UserException("User not found with id: " + userId)).when(userService).addProductToCart(userId, productId);

        mockMvc.perform(post("/api/user/1/cart", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productId.toString()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id: " + userId));
    }
    
    @Test
    void testAddProductToCartProductNotFound() throws Exception {
        Long userId = 1L;
        Long productId = 1L;

        doThrow(new UserException("Product not found with id: " + productId)).when(userService).addProductToCart(userId, productId);

        mockMvc.perform(post("/api/user/1/cart", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productId.toString()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found with id: " + productId));
    }

    @Test
    void testRemoveProductFromCart() throws Exception {
        Long userId = 1L;
        Long productId = 1L;

        doNothing().when(userService).removeProductFromCart(userId, productId);

        mockMvc.perform(delete("/api/user/{userId}/cart", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productId.toString()))
                .andExpect(status().isCreated())
                .andExpect(content().string("Product removed from cart successfully."));
    }

    @Test
    void testGetUserCart() throws Exception {
        Long userId = 1L;
        Set<Product> cartContents = new HashSet<>();
        Product product = new Product();
        product.setProductId(1L);
        product.setName("Phone");
        product.setPrice(1000);
        cartContents.add(product);

        when(userService.getUserCart(userId)).thenReturn(cartContents);

        mockMvc.perform(get("/api/user/{userId}/cart", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"productId\":1,\"name\":\"Phone\",\"price\":1000}]"));
    }

    @Test
    void testPlaceOrder() throws Exception {
        Long userId = 1L;

        doNothing().when(userService).placeOrder(userId);

        mockMvc.perform(post("/api/user/{userId}/order", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Order placed successfully."));
    }

}
