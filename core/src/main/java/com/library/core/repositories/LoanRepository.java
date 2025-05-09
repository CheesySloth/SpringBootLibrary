package com.library.core.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.core.domain.entities.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {

}
