package com.eCommerce.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerce.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
