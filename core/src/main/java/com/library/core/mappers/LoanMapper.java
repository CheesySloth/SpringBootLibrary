package com.library.core.mappers;

import com.library.core.domain.dto.LoanDto;

import com.library.core.domain.entities.Loan;

public interface LoanMapper {
    Loan fromDto(LoanDto loanDto);

    LoanDto toDto(Loan loan);
}
