package com.library.core.domain.dto;

import java.util.List;
import java.util.UUID;

import com.library.core.domain.entities.Loan;
import com.library.core.validation.UniqueEmail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record UserDto(
        UUID id,

        @NotBlank(message = "Name is required") String name,

        @Email(message = "Invalid email format") @NotBlank(message = "Name is required") @UniqueEmail // Custom //
                                                                                                      // validator
        String email,
        List<LoanDto> loans) {
}
