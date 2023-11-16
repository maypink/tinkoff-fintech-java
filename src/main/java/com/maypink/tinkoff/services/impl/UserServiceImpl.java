package com.maypink.tinkoff.services.impl;

import com.maypink.tinkoff.dto.UserDto;
import com.maypink.tinkoff.entity.CustomUser;
import com.maypink.tinkoff.repositories.UserRepository;
import com.maypink.tinkoff.security.Role;
import com.maypink.tinkoff.services.UserService;
import com.maypink.tinkoff.utils.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
//    @Transactional
    public void saveUser(UserDto userDto) {
        CustomUser customUser = new CustomUser();
        customUser.setName(userDto.getFirstName() + " " + userDto.getLastName());
        customUser.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        customUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        customUser.setRole(Role.USER);
        userRepository.save(customUser);
    }

    @Override
    public Optional<CustomUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<CustomUser> customUsers = userRepository.findAll();
        return customUsers.stream().map((user) -> userMapper.entityToDto(user))
                .collect(Collectors.toList());
    }
}
