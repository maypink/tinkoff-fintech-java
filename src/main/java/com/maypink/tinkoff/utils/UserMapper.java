package com.maypink.tinkoff.utils;

import com.maypink.tinkoff.dto.UserDto;
import com.maypink.tinkoff.entity.CustomUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto entityToDto(CustomUser customUser){
        UserDto userDto = new UserDto();
        userDto.setFirstName(customUser.getName());
        userDto.setLastName(customUser.getLastName());
        userDto.setEmail(customUser.getEmail());
        return userDto;
    }
}
