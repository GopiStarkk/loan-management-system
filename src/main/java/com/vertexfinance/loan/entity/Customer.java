package com.vertexfinance.loan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NotBlank(message = "First Name is required")
    @Column(nullable = false, length = 100)
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Column(nullable = false, length = 100)
    private String lastName;

    @Email(message = "Invalid Email")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$",
            message = "Mobile number should contain 10 digits")
    @Column(nullable = false, unique = true)
    private String mobile;

    @Pattern(regexp = "^\\d{12}$",
            message = "Aadhaar must contain 12 digits")
    @Column(nullable = false, unique = true)
    private String aadhaarNumber;

    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$",
            message = "Invalid PAN Number")
    @Column(nullable = false, unique = true)
    private String panNumber;

    @Past
    private LocalDate dateOfBirth;

    @DecimalMin(value = "1.0",
            message = "Monthly income should be greater than zero")
    private BigDecimal monthlyIncome;

    @Column(length = 50)
    private String employmentType;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

}
