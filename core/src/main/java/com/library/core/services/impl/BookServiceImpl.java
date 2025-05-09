package com.library.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.library.core.domain.dto.BookDto;
import com.library.core.domain.entities.Book;
import com.library.core.mappers.BookMapper;
import com.library.core.repositories.BookRepository;
import com.library.core.services.BookService;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    @Override
    public BookDto createBook(BookDto bookDto) {
        if (null != bookDto.id()) {
            throw new IllegalArgumentException("Book already has ID.");
        }

        if (bookDto.title().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (bookDto.author().isBlank()) {
            throw new IllegalArgumentException("Book must have an author.");
        }

        if (bookDto.loanType() == null) {
            throw new IllegalArgumentException("Book must have a loan type.");
        }

        Book bookToSave = new Book(
                null,
                bookDto.title(),
                bookDto.author(),
                bookDto.availableCopies(),
                new ArrayList<>(),
                bookDto.loanType());

        Book saved = bookRepository.save(bookToSave);
        return bookMapper.toDto(saved);
    }

    @Override
    public BookDto getBookById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookById'");
    }

    @Override
    public List<BookDto> getAllBooks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllBooks'");
    }

    @Override
    public BookDto updateBook(UUID id, BookDto updatedBook) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBook'");
    }

    @Override
    public void deleteBook(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBook'");
    }

    @Override
    public void updateAvailability(UUID id, int change) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAvailability'");
    }

    @Override
    public List<BookDto> findBooksByTitle(String title) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBooksByTitle'");
    }

    @Override
    public List<BookDto> findBooksByAuthor(String author) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBooksByAuthor'");
    }

}
