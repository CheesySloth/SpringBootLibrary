package com.library.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.core.domain.dto.LoanDto;
import com.library.core.domain.dto.UserDto;
import com.library.core.domain.entities.User;
import com.library.core.exception.UserNotFoundException;
import com.library.core.mappers.LoanMapper;
import com.library.core.mappers.UserMapper;
import com.library.core.repositories.UserRepository;
import com.library.core.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final LoanMapper loanMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, LoanMapper loanMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.loanMapper = loanMapper;
    }

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        // validateUserDtoForCreation(userDto);

        User userToSave = new User(
                null,
                userDto.name(),
                userDto.email(),
                new ArrayList<>());

        User saved = userRepository.save(userToSave);
        return userMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(UUID id) {
        return userMapper.toDto(findUserOrThrow(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public UserDto updateUser(UUID id, UserDto updatedUser) {
        User userToUpdate = findUserOrThrow(id);
        userToUpdate.setName(updatedUser.name());
        userToUpdate.setEmail(updatedUser.email());

        User saved = userRepository.save(userToUpdate);
        return userMapper.toDto(saved);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<LoanDto> getLoansForUser(UUID userId) {
        User targetUser = findUserOrThrow(userId);
        return targetUser.getLoans()
                .stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserByEmail(String email) {
        User targetUser = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("No user found with this email address: " + email));
        return userMapper.toDto(targetUser);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsersByName(String name) {
        return userRepository.findByNameIgnoreCase(name)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    // private void validateUserDtoForCreation(UserDto userDto) {
    // if (null != userDto.id()) {
    // throw new IllegalArgumentException("User already has ID.");
    // }

    // if (userDto.name().isBlank()) {
    // throw new IllegalArgumentException("Name cannot be empty");
    // }

    // if (userDto.email().isBlank()) {
    // throw new IllegalArgumentException("Email cannot be empty.");
    // }

    // if (userRepository.findByEmailIgnoreCase(userDto.email()).isPresent()) {
    // throw new IllegalArgumentException("Email is already in use");
    // }
    // }

    private User findUserOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

}
