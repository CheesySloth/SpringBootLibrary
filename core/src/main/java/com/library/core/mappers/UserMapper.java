package com.library.core.mappers;

import com.library.core.domain.dto.UserDto;
import com.library.core.domain.entities.User;

public interface UserMapper {
    User fromDto(UserDto userDto);

    UserDto toDto(User user);
}
