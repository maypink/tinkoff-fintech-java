package com.maypink.tinkoff.utils;

import com.maypink.tinkoff.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class RoleMapper {
    public Collection< ? extends GrantedAuthority> rolesToAuthorities(Role role) {
        Collection < ? extends GrantedAuthority> mapRoles = role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getDescriptor()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}
