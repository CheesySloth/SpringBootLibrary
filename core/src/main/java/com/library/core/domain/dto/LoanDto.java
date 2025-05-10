package com.library.core.domain.dto;

import java.rmi.server.UID;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.cglib.core.Local;

import com.library.core.domain.entities.Book;
import com.library.core.domain.entities.User;

import jakarta.validation.constraints.NotNull;

public record LoanDto(
        UUID id,

        @NotNull(message = "Book ID is required") UUID bookId,

        @NotNull(message = "User ID is required") UUID userId,

        @NotNull(message = "Loan date is required") LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnDate // Nullable
) {
}
