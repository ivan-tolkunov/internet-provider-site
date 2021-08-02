package ua.ivan.provider.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.READ)),
    ADMIN(Set.of(Permission.READ, Permission.WRITE));

    private final Set<Permission> permissions;

    public Set<Permission> getPermissions() {
        return permissions;
    }

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
