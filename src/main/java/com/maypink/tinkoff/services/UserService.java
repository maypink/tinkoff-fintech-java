package com.maypink.tinkoff.services;

import com.maypink.tinkoff.dto.UserDto;
import com.maypink.tinkoff.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
