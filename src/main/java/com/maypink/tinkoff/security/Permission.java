package com.maypink.tinkoff.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Permission {

    USER("USER"),

    ADMIN("ADMIN");

    private final String descriptor;
}