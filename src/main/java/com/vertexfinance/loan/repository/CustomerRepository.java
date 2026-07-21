package com.vertexfinance.loan.repository;

import com.vertexfinance.loan.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByMobile(String mobile);

    Optional<Customer> findByPanNumber(String panNumber);

    Optional<Customer> findByAadhaarNumber(String aadhaarNumber);

}
