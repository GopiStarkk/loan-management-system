package com.vertexfinance.loan.service.impl;

import com.vertexfinance.loan.dto.CustomerRequest;
import com.vertexfinance.loan.dto.CustomerResponse;
import com.vertexfinance.loan.entity.Customer;
import com.vertexfinance.loan.exception.CustomerNotFoundException;
import com.vertexfinance.loan.exception.DuplicateCustomerException;
import com.vertexfinance.loan.repository.CustomerRepository;
import com.vertexfinance.loan.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger =
        LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }



@Override
public CustomerResponse registerCustomer(CustomerRequest request) {

    // Check duplicate email
    if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new DuplicateCustomerException("Email already exists");
    }

    // Check duplicate mobile
    if (customerRepository.findByMobile(request.getMobile()).isPresent()) {
        throw new DuplicateCustomerException("Mobile number already exists");
    }

    // Check duplicate PAN
    if (customerRepository.findByPanNumber(request.getPanNumber()).isPresent()) {
        throw new DuplicateCustomerException("PAN number already exists");
    }

    // Check duplicate Aadhaar
    if (customerRepository.findByAadhaarNumber(request.getAadhaarNumber()).isPresent()) {
        throw new DuplicateCustomerException("Aadhaar number already exists");
    }

    // Convert Request DTO -> Entity
    Customer customer = Customer.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .mobile(request.getMobile())
            .aadhaarNumber(request.getAadhaarNumber())
            .panNumber(request.getPanNumber())
            .dateOfBirth(request.getDateOfBirth())
            .monthlyIncome(request.getMonthlyIncome())
            .employmentType(request.getEmploymentType())
            .city(request.getCity())
            .state(request.getState())
            .build();

    // Save to database
    Customer savedCustomer = customerRepository.save(customer);

    // Convert Entity -> Response DTO
    return CustomerResponse.builder()
            .customerId(savedCustomer.getCustomerId())
            .fullName(savedCustomer.getFirstName() + " " + savedCustomer.getLastName())
            .email(savedCustomer.getEmail())
            .city(savedCustomer.getCity())
            .state(savedCustomer.getState())
            .build();
}

@Override
public List<CustomerResponse> getAllCustomers() {

    // Fetch all customers from database
    List<Customer> customers = customerRepository.findAll();

    // Create response list
    List<CustomerResponse> responseList = new ArrayList<>();

    // Convert Entity -> Response DTO
    for (Customer customer : customers) {

        CustomerResponse response = CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .fullName(customer.getFirstName() + " " + customer.getLastName())
                .email(customer.getEmail())
                .city(customer.getCity())
                .state(customer.getState())
                .build();

        responseList.add(response);
    }

    return responseList;
}



@Override
public CustomerResponse getCustomerById(Long customerId) {

    // Find customer by ID
    Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

    // Convert Entity -> Response DTO
    return CustomerResponse.builder()
            .customerId(customer.getCustomerId())
            .fullName(customer.getFirstName() + " " + customer.getLastName())
            .email(customer.getEmail())
            .city(customer.getCity())
            .state(customer.getState())
            .build();
}

@Override
public CustomerResponse updateCustomer(Long customerId,
                                       CustomerRequest request) {

    // Find existing customer
    Customer existingCustomer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

    // Update fields
    existingCustomer.setFirstName(request.getFirstName());
    existingCustomer.setLastName(request.getLastName());
    existingCustomer.setEmail(request.getEmail());
    existingCustomer.setMobile(request.getMobile());
    existingCustomer.setAadhaarNumber(request.getAadhaarNumber());
    existingCustomer.setPanNumber(request.getPanNumber());
    existingCustomer.setDateOfBirth(request.getDateOfBirth());
    existingCustomer.setMonthlyIncome(request.getMonthlyIncome());
    existingCustomer.setEmploymentType(request.getEmploymentType());
    existingCustomer.setCity(request.getCity());
    existingCustomer.setState(request.getState());

    // Save updated customer
    Customer updatedCustomer = customerRepository.save(existingCustomer);

    // Convert Entity -> Response DTO
    return CustomerResponse.builder()
            .customerId(updatedCustomer.getCustomerId())
            .fullName(updatedCustomer.getFirstName() + " " + updatedCustomer.getLastName())
            .email(updatedCustomer.getEmail())
            .city(updatedCustomer.getCity())
            .state(updatedCustomer.getState())
            .build();
}

@Override
public void deleteCustomer(Long customerId) {

    // Find customer
    Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

    // Delete customer
    customerRepository.delete(customer);
}
}


