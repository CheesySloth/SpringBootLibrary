package com.library.core.domain.dto;

import java.util.List;
import java.util.UUID;

import com.library.core.domain.entities.LoanType;

public record BookDto(
        UUID id,
        String title,
        String author,
        int availableCopies,
        List<LoanDto> loans,
        LoanType loanType) {

}
