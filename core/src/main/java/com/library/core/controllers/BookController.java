package com.library.core.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.core.domain.dto.BookDto;
import com.library.core.services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // CRUD methods
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookDto));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable("id") UUID id,
            @RequestBody @Valid BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Business Logic methods
    @GetMapping("/search") // Cannot have multiple get mappings to the same address
    public ResponseEntity<List<BookDto>> findBooksByTitleOrAuthor(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {

        if (title != null)
            return ResponseEntity.ok(bookService.findBooksByTitle(title));
        if (author != null)
            return ResponseEntity.ok(bookService.findBooksByAuthor(author));
        // fallback
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PatchMapping("{id}/copies")
    public ResponseEntity<Void> updateBookAvailability(
            @PathVariable("id") UUID id,
            @RequestBody int change) {
        bookService.updateAvailability(id, change);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
