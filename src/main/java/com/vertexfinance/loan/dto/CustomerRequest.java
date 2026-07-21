package com.vertexfinance.loan.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequest {

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Mobile number must be 10 digits"
    )
    private String mobile;

    @NotBlank(message = "Aadhaar number is required")
    @Pattern(
            regexp = "^[0-9]{12}$",
            message = "Aadhaar number must be 12 digits"
    )
    private String aadhaarNumber;

    @NotBlank(message = "PAN number is required")
    @Pattern(
            regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$",
            message = "Invalid PAN Number"
    )
    private String panNumber;

    @Past(message = "Date of Birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Monthly income is required")
    @Positive(message = "Monthly income must be greater than zero")
    private BigDecimal monthlyIncome;

    @NotBlank(message = "Employment Type is required")
    private String employmentType;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;
}
