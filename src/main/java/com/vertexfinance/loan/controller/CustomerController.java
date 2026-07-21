package com.vertexfinance.loan.controller;

import com.vertexfinance.loan.dto.CustomerRequest;
import com.vertexfinance.loan.dto.CustomerResponse;
import com.vertexfinance.loan.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.vertexfinance.loan.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create Customer
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> registerCustomer(
        @Valid @RequestBody CustomerRequest request) {

    CustomerResponse response = customerService.registerCustomer(request);

    ApiResponse<CustomerResponse> apiResponse =
            ApiResponse.<CustomerResponse>builder()
                    .success(true)
                    .message("Customer registered successfully")
                    .timestamp(LocalDateTime.now())
                    .data(response)
                    .build();

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(apiResponse);
}

    // Get All Customers
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {

    List<CustomerResponse> customers = customerService.getAllCustomers();

    ApiResponse<List<CustomerResponse>> response =
            ApiResponse.<List<CustomerResponse>>builder()
                    .success(true)
                    .message("Customers fetched successfully")
                    .timestamp(LocalDateTime.now())
                    .data(customers)
                    .build();

    return ResponseEntity.ok(response);
}

    // Get Customer By ID
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
        @PathVariable Long customerId) {

    CustomerResponse customer = customerService.getCustomerById(customerId);

    ApiResponse<CustomerResponse> response =
            ApiResponse.<CustomerResponse>builder()
                    .success(true)
                    .message("Customer fetched successfully")
                    .timestamp(LocalDateTime.now())
                    .data(customer)
                    .build();

    return ResponseEntity.ok(response);
}

    // Update Customer
    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
        @PathVariable Long customerId,
        @Valid @RequestBody CustomerRequest request) {

    CustomerResponse customer =
            customerService.updateCustomer(customerId, request);

    ApiResponse<CustomerResponse> response =
            ApiResponse.<CustomerResponse>builder()
                    .success(true)
                    .message("Customer updated successfully")
                    .timestamp(LocalDateTime.now())
                    .data(customer)
                    .build();

    return ResponseEntity.ok(response);
}

    // Delete Customer
    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(
        @PathVariable Long customerId) {

    customerService.deleteCustomer(customerId);

    ApiResponse<String> response =
            ApiResponse.<String>builder()
                    .success(true)
                    .message("Customer deleted successfully")
                    .timestamp(LocalDateTime.now())
                    .data("Customer deleted successfully")
                    .build();

    return ResponseEntity.ok(response);
}

}
