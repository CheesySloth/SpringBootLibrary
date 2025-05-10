package com.library.core.domain.dto;

import java.util.List;
import java.util.UUID;

import com.library.core.domain.entities.LoanType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BookDto(
        UUID id,

        @NotBlank(message = "Title is required") String title,

        @NotBlank(message = "Author is required") String author,

        @Min(value = 0, message = "Available copies must be non-negative") int availableCopies,
        List<LoanDto> loans,
        LoanType loanType) {
}
