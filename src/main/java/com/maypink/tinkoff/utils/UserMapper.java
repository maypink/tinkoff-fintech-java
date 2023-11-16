package com.maypink.tinkoff.utils;

import com.maypink.tinkoff.dto.UserDto;
import com.maypink.tinkoff.entity.CustomUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto entityToDto(CustomUser customUser){
        UserDto userDto = new UserDto();
        String[] name = customUser.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(customUser.getEmail());
        return userDto;
    }
}
