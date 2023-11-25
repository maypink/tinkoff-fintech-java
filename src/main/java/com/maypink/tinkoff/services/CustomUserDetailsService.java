package com.maypink.tinkoff.services;

import com.maypink.tinkoff.entity.CustomUser;
import com.maypink.tinkoff.repositories.UserRepository;
import com.maypink.tinkoff.utils.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleMapper roleMapper;

    public CustomUserDetailsService(UserRepository userRepository, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<CustomUser> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                    user.get().getPassword(),
                    roleMapper.rolesToAuthorities(user.get().getRole()));
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}


