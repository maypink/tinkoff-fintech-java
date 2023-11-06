package com.maypink.tinkoff.services;

import com.maypink.tinkoff.dto.UserDto;
import com.maypink.tinkoff.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto userDto);

    Optional<User> findByEmail(String email);

    List<UserDto> findAllUsers();
}
