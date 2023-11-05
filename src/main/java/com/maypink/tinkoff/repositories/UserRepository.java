package com.maypink.tinkoff.repositories;

import com.maypink.tinkoff.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
