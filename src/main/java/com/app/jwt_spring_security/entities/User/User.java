package com.app.jwt_spring_security.entities.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails { // Para que esta entidad pueda trabajar con la autenticación, debe implementar la interfaz UseDetails
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String username;
    String firstname;
    String lastname;
    String country;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())); // Contiene un único objeto que representa la autoridad otorgada al usuario autenticado
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Devolvemos true porque el mismo token es el que va a indicar si estos parámetros se cumplen o no
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Devolvemos true porque el mismo token es el que va a indicar si estos parámetros se cumplen o no
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Devolvemos true porque el mismo token es el que va a indicar si estos parámetros se cumplen o no
    }

    @Override
    public boolean isEnabled() {
        return true; // Devolvemos true porque el mismo token es el que va a indicar si estos parámetros se cumplen o no
    }
}
