package com.library.core.services;

import java.util.List;
import java.util.UUID;

import com.library.core.domain.dto.BookDto;

public interface BookService {
    BookDto createBook(BookDto bookDto);

    BookDto getBookById(UUID id);

    List<BookDto> getAllBooks();

    BookDto updateBook(UUID id, BookDto updatedBook);

    void deleteBook(UUID id);

    // Business logic
    void updateAvailability(UUID id, int change);

    List<BookDto> findBooksByTitle(String title);

    List<BookDto> findBooksByAuthor(String author);
}
