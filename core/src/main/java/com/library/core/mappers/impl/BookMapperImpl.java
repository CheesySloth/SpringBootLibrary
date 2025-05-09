package com.library.core.mappers.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.core.domain.dto.BookDto;
import com.library.core.domain.entities.Book;
import com.library.core.mappers.BookMapper;
import com.library.core.mappers.LoanMapper;

@Component
public class BookMapperImpl implements BookMapper {
    private LoanMapper loanMapper;

    @Autowired
    public BookMapperImpl(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Override
    public Book fromDto(BookDto bookDto) {
        return new Book(
                bookDto.id(),
                bookDto.title(),
                bookDto.author(),
                bookDto.availableCopies(),
                Optional.ofNullable(bookDto.loans())
                        .map(loans -> loans.stream().map(
                                loanMapper::fromDto)
                                .toList())
                        .orElseGet(ArrayList::new)

        );
    }

    @Override
    public BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getAvailableCopies(),
                Optional.ofNullable(book.getLoans())
                        .map(loans -> loans.stream().map(
                                loanMapper::toDto)
                                .toList())
                        .orElseGet(ArrayList::new));
    }

}
