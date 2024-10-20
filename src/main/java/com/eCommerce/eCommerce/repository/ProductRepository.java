package com.eCommerce.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
}
