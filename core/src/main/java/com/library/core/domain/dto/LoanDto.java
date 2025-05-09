package com.library.core.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.cglib.core.Local;

import com.library.core.domain.entities.Book;
import com.library.core.domain.entities.User;

public record LoanDto(
        UUID id,
        UUID bookId,
        UUID userId,
        LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnDate // Nullable
) {

}
