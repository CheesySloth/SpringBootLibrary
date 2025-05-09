package com.library.core.services;

import java.util.List;
import java.util.UUID;

import com.library.core.domain.dto.LoanDto;

public interface LoanService {
    // CRUDA
    LoanDto createLoan(LoanDto loanDto);

    LoanDto getLoanById(UUID id);

    List<LoanDto> getAllLoans();

    LoanDto updateLoan(UUID id, LoanDto loanDto);

    void deleteLoan(UUID id);

    // Business Logic
    List<LoanDto> getLoansByUserId(UUID userId);

    List<LoanDto> getLoansByBookId(UUID bookId);

    void returnBook(UUID loanId);
}
