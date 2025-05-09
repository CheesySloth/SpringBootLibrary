package com.library.core.services;

import java.util.List;
import java.util.UUID;

import com.library.core.domain.dto.LoanDto;
import com.library.core.domain.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(UUID id);

    List<UserDto> getAllUsers();

    UserDto updateUser(UUID id, UserDto updatedUser);

    void deleteUser(UUID id);

    List<LoanDto> getLoansForUser(UUID userId);

    UserDto getUserByEmail(String email);

    List<UserDto> getUsersByName(String name);
}
