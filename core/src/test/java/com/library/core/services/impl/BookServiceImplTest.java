package com.library.core.services.impl;

import com.library.core.domain.dto.BookDto;
import com.library.core.domain.dto.LoanDto;
import com.library.core.domain.entities.Book;
import com.library.core.mappers.BookMapper;
import com.library.core.domain.entities.LoanType;
import com.library.core.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {
    private BookRepository bookRepository;
    private BookMapper bookMapper;
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookMapper = mock(BookMapper.class);
        bookService = new BookServiceImpl(bookRepository, bookMapper);
    }

    @Test
    void createBook_shouldSaveBookAndReturnDto() {
        // Arrange
        List<LoanDto> emptyLoanList = new ArrayList<>();

        BookDto inputDto = new BookDto(
                null,
                "The Hobbit",
                "J.R.R. Tolkien",
                5,
                emptyLoanList,
                LoanType.SHORT_TERM);

        // For reference
        Book bookToSave = new Book(
                null,
                "The Hobbit",
                "J.R.R. Tolkien",
                5,
                new ArrayList<>(),
                LoanType.SHORT_TERM);

        Book savedBook = new Book(
                UUID.randomUUID(),
                "The Hobbit",
                "J.R.R. Tolkien",
                5,
                new ArrayList<>(),
                LoanType.SHORT_TERM);

        BookDto expectedDto = new BookDto(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthor(),
                savedBook.getAvailableCopies(),
                emptyLoanList,
                savedBook.getLoanType());

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(expectedDto);

        // Act
        BookDto result = bookService.createBook(inputDto);

        // Assert
        assertEquals(expectedDto, result);

        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookCaptor.capture());

        Book captured = bookCaptor.getValue();
        assertEquals("The Hobbit", captured.getTitle());
        assertEquals("J.R.R. Tolkien", captured.getAuthor());
        assertEquals(5, captured.getAvailableCopies());
        assertEquals(LoanType.SHORT_TERM, captured.getLoanType());
        assertTrue(captured.getLoans().isEmpty());
    }
}
