package com.vertexfinance.loan.service;

import com.vertexfinance.loan.dto.CustomerRequest;
import com.vertexfinance.loan.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse registerCustomer(CustomerRequest request);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Long customerId);

    CustomerResponse updateCustomer(Long customerId,
                                    CustomerRequest request);

    void deleteCustomer(Long customerId);

}
