package com.library.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.core.domain.dto.BookDto;
import com.library.core.domain.entities.Book;
import com.library.core.mappers.BookMapper;
import com.library.core.repositories.BookRepository;

import com.library.core.services.BookService;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    @Override
    public BookDto createBook(BookDto bookDto) {
        validateBookDtoForCreation(bookDto);

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

    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> getBookById(UUID id) {
        // Approach better when service reused in multiple contexts and caller needs
        // flexibility
        return bookRepository.findById(id).map(bookMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public BookDto updateBook(UUID id, BookDto updatedBook) {
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book does not exist in database"));
        bookToUpdate.setTitle(updatedBook.title());
        bookToUpdate.setAuthor(updatedBook.author());
        bookToUpdate.setAvailableCopies(updatedBook.availableCopies());
        // Don't update loans as this is handled by LoansService
        bookToUpdate.setLoanType(updatedBook.loanType());

        Book updated = bookRepository.save(bookToUpdate);
        return bookMapper.toDto(updated);
    }

    @Override
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateAvailability(UUID id, int change) {
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book does not exist in database"));

        int newNumberOfCopies = bookToUpdate.getAvailableCopies() + change;

        if (newNumberOfCopies < 0)
            throw new IllegalArgumentException("Cannot have a negative number of copies of a book.");

        bookToUpdate.setAvailableCopies(newNumberOfCopies);

        bookRepository.save(bookToUpdate);
    }

    @Override
    public List<BookDto> findBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> findBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    private void validateBookDtoForCreation(BookDto bookDto) {
        if (null != bookDto.id()) {
            throw new IllegalArgumentException("User already has ID.");
        }

        if (bookDto.title().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (bookDto.author().isBlank()) {
            throw new IllegalArgumentException("Email must have an author.");
        }

        if (bookDto.loanType() == null) {
            throw new IllegalArgumentException("Book must have a loan type.");
        }
    }

}
