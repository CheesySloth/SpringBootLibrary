package com.library.core.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @JsonBackReference
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public Loan() {
    }

    public Loan(UUID id, Book book, User user, LocalDate loanDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusDays(book.getLoanType().getDuration());
        this.returnDate = null;

    }

    public Loan(UUID id, Book book, User user, LocalDate loanDate, LocalDate returnDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusDays(book.getLoanType().getDuration());
        this.returnDate = returnDate;

    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getLoanDate() {
        return this.loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LoanType getLoanType() {
        return this.loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Loan)) {
            return false;
        }
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id) && Objects.equals(book, loan.book) && Objects.equals(user, loan.user)
                && Objects.equals(loanDate, loan.loanDate) && Objects.equals(returnDate, loan.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, user, loanDate, returnDate);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", book='" + getBook() + "'" +
                ", user='" + getUser() + "'" +
                ", loanDate='" + getLoanDate() + "'" +
                ", returnDate='" + getReturnDate() + "'" +
                "}";
    }

}
