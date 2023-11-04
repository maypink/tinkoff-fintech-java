package com.maypink.tinkoff.secutrity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DatabaseUserPasswordService implements UserDetailsPasswordService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        UserCredentials userCredentials = userRepository.findByUsername(user.getUsername());
        userCredentials.setPassword(newPassword);
        return userMapper.toUserDetails(userCredentials);
    }
}
