package com.maypink.tinkoff.secutrity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserCredentials {

    @Id
    private String username;

    private String password;

    @ElementCollection
    @JoinTable(
            name = "authorities",
            joinColumns = {@JoinColumn(name = "username")})
    @Column(name = "authority")
    private Set<String> roles;
}
