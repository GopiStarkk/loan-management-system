package com.vertexfinance.loan.service.impl;

import com.vertexfinance.loan.dto.CustomerRequest;
import com.vertexfinance.loan.dto.CustomerResponse;
import com.vertexfinance.loan.entity.Customer;
import com.vertexfinance.loan.repository.CustomerRepository;
import com.vertexfinance.loan.exception.DuplicateCustomerException;
import com.vertexfinance.loan.exception.CustomerNotFoundException;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
void shouldRegisterCustomerSuccessfully() {

    // Arrange
    CustomerRequest request = new CustomerRequest();

    request.setFirstName("Rahul");
    request.setLastName("Sharma");
    request.setEmail("rahul@test.com");
    request.setMobile("9876543211");
    request.setAadhaarNumber("987654321012");
    request.setPanNumber("ABCDE1234F");
    request.setDateOfBirth(LocalDate.of(1995, 8, 20));
    request.setMonthlyIncome(new BigDecimal("50000"));
    request.setEmploymentType("Developer");
    request.setCity("Chennai");
    request.setState("Tamil Nadu");

    Customer savedCustomer = Customer.builder()
            .customerId(1L)
            .firstName("Rahul")
            .lastName("Sharma")
            .email("rahul@test.com")
            .mobile("9876543211")
            .aadhaarNumber("987654321012")
            .panNumber("ABCDE1234F")
            .dateOfBirth(LocalDate.of(1995, 8, 20))
            .monthlyIncome(new BigDecimal("50000"))
            .employmentType("Developer")
            .city("Chennai")
            .state("Tamil Nadu")
            .build();

    when(customerRepository.findByEmail(anyString()))
            .thenReturn(Optional.empty());

    when(customerRepository.findByMobile(anyString()))
            .thenReturn(Optional.empty());

    when(customerRepository.findByPanNumber(anyString()))
            .thenReturn(Optional.empty());

    when(customerRepository.findByAadhaarNumber(anyString()))
            .thenReturn(Optional.empty());

    when(customerRepository.save(any(Customer.class)))
            .thenReturn(savedCustomer);

    // Act
    CustomerResponse response = customerService.registerCustomer(request);

    // Assert
    Assertions.assertNotNull(response);
    Assertions.assertEquals(1L, response.getCustomerId());
    Assertions.assertEquals("Rahul Sharma", response.getFullName());
    Assertions.assertEquals("rahul@test.com", response.getEmail());

    verify(customerRepository, times(1))
            .save(any(Customer.class));
}

     @Test
void shouldThrowExceptionWhenEmailAlreadyExists() {

    // Arrange
    CustomerRequest request = new CustomerRequest();

    request.setFirstName("Rahul");
    request.setLastName("Sharma");
    request.setEmail("rahul@test.com");
    request.setMobile("9876543211");
    request.setAadhaarNumber("987654321012");
    request.setPanNumber("ABCDE1234F");
    request.setDateOfBirth(LocalDate.of(1995, 8, 20));
    request.setMonthlyIncome(new BigDecimal("50000"));
    request.setEmploymentType("Developer");
    request.setCity("Chennai");
    request.setState("Tamil Nadu");

    Customer existingCustomer = Customer.builder()
            .customerId(1L)
            .email("rahul@test.com")
            .build();

    when(customerRepository.findByEmail(anyString()))
            .thenReturn(Optional.of(existingCustomer));

    // Act & Assert
    DuplicateCustomerException exception =
            Assertions.assertThrows(
                    DuplicateCustomerException.class,
                    () -> customerService.registerCustomer(request)
            );

    Assertions.assertEquals(
            "Email already exists",
            exception.getMessage()
    );

    verify(customerRepository, times(1))
            .findByEmail(anyString());

    verify(customerRepository, never())
            .save(any(Customer.class));
}

@Test
void shouldThrowCustomerNotFoundException() {

    // Arrange

    when(customerRepository.findById(100L))
            .thenReturn(Optional.empty());

    // Act & Assert

    CustomerNotFoundException exception =
            Assertions.assertThrows(
                    CustomerNotFoundException.class,
                    () -> customerService.getCustomerById(100L)
            );

    Assertions.assertEquals(
            "Customer not found",
            exception.getMessage());

    verify(customerRepository, times(1))
            .findById(100L);

}

@Test
void shouldUpdateCustomerSuccessfully() {

    Customer existing = Customer.builder()
            .customerId(1L)
            .firstName("Rahul")
            .build();

    CustomerRequest request = new CustomerRequest();

    request.setFirstName("Updated");

    request.setLastName("Sharma");

    request.setEmail("rahul@test.com");

    request.setMobile("9876543211");

    request.setAadhaarNumber("987654321012");

    request.setPanNumber("ABCDE1234F");

    request.setDateOfBirth(LocalDate.of(1995,8,20));

    request.setMonthlyIncome(new BigDecimal("60000"));

    request.setEmploymentType("Developer");

    request.setCity("Chennai");

    request.setState("Tamil Nadu");

    when(customerRepository.findById(1L))
            .thenReturn(Optional.of(existing));

    when(customerRepository.save(any(Customer.class)))
            .thenReturn(existing);

    CustomerResponse response =
            customerService.updateCustomer(1L, request);

    Assertions.assertNotNull(response);

    verify(customerRepository)
            .save(any(Customer.class));

}

@Test
void shouldDeleteCustomerSuccessfully() {

    Customer customer = Customer.builder()
            .customerId(1L)
            .build();

    when(customerRepository.findById(1L))
            .thenReturn(Optional.of(customer));

    customerService.deleteCustomer(1L);

    verify(customerRepository)
            .delete(customer);

}

}
