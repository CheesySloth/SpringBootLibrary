package com.library.core.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.core.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByEmailIgnoreCase(String email);

    public List<User> findByNameContainingIgnoreCase(String name);

}
