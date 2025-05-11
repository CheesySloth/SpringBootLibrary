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

import com.library.core.domain.dto.LoanDto;
import com.library.core.services.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/loans")
public class LoanController {

    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // CRUD methods
    @GetMapping
    public ResponseEntity<List<LoanDto>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LoanDto> getLoan(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @PostMapping
    public ResponseEntity<LoanDto> createLoan(@RequestBody @Valid LoanDto loanDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(loanDto));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<LoanDto> updateLoan(
            @PathVariable("id") UUID id,
            @RequestBody @Valid LoanDto loanDto) {
        return ResponseEntity.ok(loanService.updateLoan(id, loanDto));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable("id") UUID id) {
        loanService.deleteLoan(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Business Logic
    @GetMapping(path = "/search")
    public ResponseEntity<List<LoanDto>> getLoansByUserOrBookId(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID bookId) {
        if (null != userId)
            return ResponseEntity.ok(loanService.getLoansByUserId(userId));
        if (null != bookId)
            return ResponseEntity.ok(loanService.getLoansByBookId(bookId));

        // Fallback
        return getAllLoans();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Void> returnBook(@PathVariable("id") UUID loanId) {
        loanService.returnBook(loanId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
