package com.library.core.mappers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.core.domain.dto.LoanDto;
import com.library.core.domain.entities.Loan;
import com.library.core.domain.entities.User;
import com.library.core.mappers.LoanMapper;
import com.library.core.repositories.BookRepository;
import com.library.core.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import com.library.core.domain.entities.Book;

@Component
public class LoanMapperImpl implements LoanMapper {
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Autowired
    public LoanMapperImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Loan fromDto(LoanDto loanDto) {

        Book book = bookRepository.findById(loanDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found for this loan"));

        User user = userRepository.findById(loanDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found for this loan"));

        return new Loan(
                loanDto.id(),
                book,
                user,
                loanDto.loanDate(),
                loanDto.returnDate());
    }

    @Override
    public LoanDto toDto(Loan loan) {
        return new LoanDto(
                loan.getId(),
                loan.getBook().getId(),
                loan.getUser().getId(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate());
    }
}
