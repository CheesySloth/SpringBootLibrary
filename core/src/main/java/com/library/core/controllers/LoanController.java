package com.library.core.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.RequestEntity;
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

@RestController
@RequestMapping(path = "/loans")
public class LoanController {

    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // CRUD methods
    @GetMapping
    public List<LoanDto> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping(path = "/{id}")
    public LoanDto getLoan(@PathVariable("id") UUID id) {
        return loanService.getLoanById(id);
    }

    @PostMapping
    public ResponseEntity<LoanDto> createLoan(@RequestBody LoanDto loanDto) {
        return ResponseEntity.ok(loanService.createLoan(loanDto));
    }

    @PutMapping(path = "/{id}")
    public LoanDto updateLoan(
            @PathVariable("id") UUID id,
            @RequestBody LoanDto loanDto) {
        return loanService.updateLoan(id, loanDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteLoan(@PathVariable("id") UUID id) {
        loanService.deleteLoan(id);
    }

    // Business Logic
    @GetMapping(path = "/search")
    public List<LoanDto> getLoansByUserOrBookId(
            @RequestParam UUID userId,
            @RequestParam UUID bookId) {
        if (null != userId)
            return loanService.getLoansByUserId(userId);
        if (null != bookId)
            return loanService.getLoansByBookId(bookId);

        // Fallback
        return getAllLoans();
    }

    @PatchMapping(path = "/{id}")
    public void returnBook(@PathVariable("id") UUID loanId) {
        loanService.returnBook(loanId);
    }

}
