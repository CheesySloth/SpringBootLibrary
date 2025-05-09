package com.library.core.mappers.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.core.domain.dto.UserDto;
import com.library.core.domain.entities.User;
import com.library.core.mappers.LoanMapper;
import com.library.core.mappers.UserMapper;

@Component
public class UserMapperImpl implements UserMapper {

    private LoanMapper loanMapper;

    @Autowired
    public UserMapperImpl(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Override
    public User fromDto(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.name(),
                userDto.email(),
                Optional.ofNullable(userDto.loans())
                        .map(loans -> loans.stream().map(loanMapper::fromDto)
                                .toList())
                        .orElseGet(ArrayList::new));
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                Optional.ofNullable(user.getLoans())
                        .map(
                                loans -> loans.stream().map(loanMapper::toDto)
                                        .toList())
                        .orElseGet(ArrayList::new));
    }

}
