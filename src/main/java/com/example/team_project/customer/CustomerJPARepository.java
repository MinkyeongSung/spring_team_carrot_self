package com.example.team_project.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerJPARepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer c where c.user.id = :id")
    Customer findByCustomerId(@Param("id") Integer id);
}
