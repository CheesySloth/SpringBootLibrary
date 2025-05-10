package com.library.core.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.core.domain.dto.LoanDto;
import com.library.core.domain.entities.Loan;
import com.library.core.domain.entities.Book;
import com.library.core.domain.entities.User;
import com.library.core.exception.BookNotFoundException;
import com.library.core.exception.LoanNotFoundException;
import com.library.core.exception.UserNotFoundException;
import com.library.core.mappers.LoanMapper;
import com.library.core.repositories.BookRepository;
import com.library.core.repositories.LoanRepository;
import com.library.core.repositories.UserRepository;
import com.library.core.services.BookService;
import com.library.core.services.LoanService;

@Service
public class LoanServiceImpl implements LoanService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BookService bookService;

    public LoanServiceImpl(
            BookRepository bookRepository,
            UserRepository userRepository,
            LoanRepository loanRepository,
            LoanMapper loanMapper,
            BookService bookService) {
        this.bookRepository = bookRepository;
        this.loanMapper = loanMapper;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    @Transactional
    @Override
    public LoanDto createLoan(LoanDto loanDto) {
        // validateLoanDtoForCreation(loanDto);
        Book book = bookRepository.findById(loanDto.bookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + loanDto.bookId()));
        User user = userRepository.findById(loanDto.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + loanDto.userId()));

        // Update book availability
        bookService.updateAvailability(loanDto.bookId(), -1);

        Loan loanToSave = new Loan(
                null,
                book,
                user,
                LocalDate.now());

        Loan saved = loanRepository.save(loanToSave);
        return loanMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public LoanDto getLoanById(UUID id) {
        return loanMapper.toDto(findLoanOrThrow(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<LoanDto> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public LoanDto updateLoan(UUID id, LoanDto loanDto) {
        Loan loanToUpdate = findLoanOrThrow(id);

        Book book = bookRepository.findById(loanDto.bookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + loanDto.bookId()));

        User user = userRepository.findById(loanDto.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + loanDto.userId()));

        loanToUpdate.setBook(book);
        loanToUpdate.setUser(user);
        loanToUpdate.setLoanDate(loanDto.loanDate());
        loanToUpdate.setReturnDate(loanDto.returnDate());

        Loan updated = loanRepository.save(loanToUpdate);
        return loanMapper.toDto(updated);
    }

    @Override
    public void deleteLoan(UUID id) {
        loanRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<LoanDto> getLoansByUserId(UUID userId) {
        return loanRepository.findByUserId(userId)
                .stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<LoanDto> getLoansByBookId(UUID bookId) {
        return loanRepository.findByBookId(bookId)
                .stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void returnBook(UUID loanId) {
        Loan loanToSave = findLoanOrThrow(loanId);

        if (loanToSave.getReturnDate() != null) {
            throw new IllegalStateException("This book has already been returned.");
        }

        // Set return date to today
        loanToSave.setReturnDate(LocalDate.now());

        // Increase book availability by 1
        bookService.updateAvailability(loanToSave.getBook().getId(), 1);

        loanRepository.save(loanToSave);
    }

    // private void validateLoanDtoForCreation(LoanDto loanDto) {
    // if (null != loanDto.id()) {
    // throw new IllegalArgumentException("Loan already has ID.");
    // }

    // if (null == loanDto.bookId()) {
    // throw new IllegalArgumentException("Book ID must be provided to create a
    // loan.");
    // }

    // if (null == loanDto.userId()) {
    // throw new IllegalArgumentException("User ID must be provided to create a
    // loan.");
    // }
    // }

    private Loan findLoanOrThrow(UUID id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id " + id));
    }
}
