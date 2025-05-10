package com.library.core.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.core.domain.dto.BookDto;
import com.library.core.mappers.BookMapper;
import com.library.core.services.BookService;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    private BookService bookService;
    private BookMapper bookMapper;

    public BookController(BookService bookService) {
        this.bookService = bookService;
        this.bookMapper = null;
    }

    // CRUD methods
    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "/{book_id}")
    public Optional<BookDto> getBook(@PathVariable("book_id") UUID id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookDto createBook(@RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @PutMapping(path = "/{book_id}")
    public void updateBook(
            @PathVariable("book_id") UUID id,
            @RequestBody BookDto bookDto) {
        bookService.updateBook(id, bookDto);
    }

    @DeleteMapping(path = "/{book_id}")
    public void deleteBook(@PathVariable("book_id") UUID id) {
        bookService.deleteBook(id);
    }

    // Business Logic methods
    @GetMapping
    public List<BookDto> findBooksByTitle(@RequestBody String title) {
        return bookService.findBooksByTitle(title);
    }

    @GetMapping
    public List<BookDto> findBooksByAuthor(@RequestBody String author) {
        return bookService.findBooksByAuthor(author);
    }

    @PatchMapping("{book_id}/availableCopies")
    public void updateBookAvailability(
            @PathVariable("book_id") UUID id,
            @RequestBody int change) {
        bookService.updateAvailability(id, change);
    }

}
