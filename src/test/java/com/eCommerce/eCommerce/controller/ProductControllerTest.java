package com.eCommerce.eCommerce.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.repository.ProductRepository;
import com.eCommerce.eCommerce.service.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    
    @MockBean
    private ProductRepository productRepository;

    List<Product> listProduct;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        listProduct = new ArrayList<>();
        Product product1 = new Product();
		product1.setDescription("Latest model smartphone with 128GB storage.");
		product1.setImageUrl("https://example.com/images/smartphone.jpg");
		product1.setName("Phone");
		product1.setPrice(1000);
		
		Product product2 = new Product();
		product2.setDescription("Washing Machine Automatic");
		product2.setImageUrl("https://example.com/images/smartphone.jpg");
		product2.setName("Washing Machine");
		product2.setPrice(10000);
		
		listProduct.add(product1);
		listProduct.add(product2);
		
    }
    
	@Test
	void testCreateProduct() throws Exception {
		

        when(productService.createProduct(any())).thenReturn(listProduct.get(0));

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Phone\", \"description\":\"Latest model smartphone with 128GB storage.\", \"price\":1000, \"imageUrl\":\"https://example.com/images/smartphone.jpg\"}"))
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("$.name").value("Phone"))
		        .andExpect(jsonPath("$.description").value("Latest model smartphone with 128GB storage."))
		        .andExpect(jsonPath("$.price").value(1000))
		        .andExpect(jsonPath("$.imageUrl").value("https://example.com/images/smartphone.jpg"));
	}

	@Test
	void testGetAllProducts() throws Exception {
		when(productService.getAllProducts()).thenReturn(listProduct);
        mockMvc.perform(get("/api/products"))
        		.andExpect(status().isOk());
    }

	@Test
	void testGetProductById() throws Exception {
		when(productService.getProductById(Long.valueOf(1))).thenReturn(Optional.of(listProduct.get(0)));
        mockMvc.perform(get("/api/products/1"))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.name").value("Phone"))
		        .andExpect(jsonPath("$.description").value("Latest model smartphone with 128GB storage."))
		        .andExpect(jsonPath("$.price").value(1000))
		        .andExpect(jsonPath("$.imageUrl").value("https://example.com/images/smartphone.jpg"));
	}
	
	@Test
	void testGetProductByIdWhenNotInDB() throws Exception {
		when(productService.getProductById(Long.valueOf(1))).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/products/1"))
        		.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateProduct() throws Exception {
		when(productService.updateProduct(any(),any())).thenReturn(listProduct.get(0));
        mockMvc.perform(put("/api/products/1")
        		.contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Phone\", \"description\":\"Latest model smartphone with 128GB storage.\", \"price\":1000, \"imageUrl\":\"https://example.com/images/smartphone.jpg\"}"))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.name").value("Phone"))
		        .andExpect(jsonPath("$.description").value("Latest model smartphone with 128GB storage."))
		        .andExpect(jsonPath("$.price").value(1000))
		        .andExpect(jsonPath("$.imageUrl").value("https://example.com/images/smartphone.jpg"));
	}

	@Test
	void testDeleteProduct() throws Exception {
		doNothing().when(productService).deleteProduct(Long.valueOf(1));
        mockMvc.perform(delete("/api/products/1"))
        		.andExpect(status().isNoContent());
	}

}
