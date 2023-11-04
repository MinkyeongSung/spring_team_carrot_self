package com.example.team_project.board;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.team_project.product.Product;

public class BoardJPARepositoryTest {
    // @Modifying
    // @Query("UPDATE Product p SET p.productPrice = :productPrice, p.productDescription = :productDescription, p.productName = :productName WHERE p.id = :productId")
    // void mUpdateProduct(@Param("productId") int productId, @Param("productPrice") int productPrice,
    //         @Param("productDescription") String productDescription, @Param("productName") String productName);

    // @Query("SELECT p FROM Product p WHERE p.id = :productId")
    // Product findProductById(@Param("productId") int productId);

    // List<Product> findByProductName(String keyword);

    // List<Product> findByProductNameContaining(String keyword);
}
