package com.maypink.tinkoff.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN(Set.of(Permission.values())),

    USER(Set.of(
            Permission.USER
    ));

    private final Set<Permission> permissions;
}
