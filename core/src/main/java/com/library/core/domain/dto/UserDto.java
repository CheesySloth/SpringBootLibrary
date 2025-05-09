package com.library.core.domain.dto;

import java.util.List;
import java.util.UUID;

import com.library.core.domain.entities.Loan;

public record UserDto(
        UUID id,
        String name,
        String email,
        List<LoanDto> loans) {

}
