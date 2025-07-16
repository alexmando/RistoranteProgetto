package org.example.ristoranteprogetto.security;

import org.example.ristoranteprogetto.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class UserDetailsImpl implements UserDetails {


    public final UserEntity user;  // la tua User entity

    public UserDetailsImpl(UserEntity user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getEmail();  // user.getUsername() o getEmail() dipende da te
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // la password hashata nel DB
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // user.getRuolo() restituisce un enum Ruolo
        if (user.getRuolo() != null) {
            String ruolo = "ROLE_" + user.getRuolo().name().toUpperCase();
            System.out.println("Ruolo convertito: " + ruolo); // DEBUG
            return List.of(new SimpleGrantedAuthority(ruolo));
        }
        return List.of();
    }
    // altri metodi come isEnabled, isAccountNonLocked ecc. li implementi qui

}
