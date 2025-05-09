package com.library.core.domain.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "available_copies")
    private int availableCopies;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<Loan> loans = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;

    public Book() {
    }

    public Book(UUID id, String title, String author, int availableCopies, List<Loan> loans, LoanType loanType) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
        this.loans = loans;
        this.loanType = loanType;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAvailableCopies() {
        return this.availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public List<Loan> getLoans() {
        return this.loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
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
        if (!(o instanceof Book)) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author)
                && availableCopies == book.availableCopies && Objects.equals(loans, book.loans)
                && Objects.equals(loanType, book.loanType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, availableCopies, loans, loanType);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", title='" + getTitle() + "'" +
                ", author='" + getAuthor() + "'" +
                ", availableCopies='" + getAvailableCopies() + "'" +
                ", loans='" + getLoans() + "'" +
                ", loanType='" + getLoanType() + "'" +
                "}";
    }

}
