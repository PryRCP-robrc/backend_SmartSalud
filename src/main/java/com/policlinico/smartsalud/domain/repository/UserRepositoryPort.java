package com.policlinico.smartsalud.domain.repository;

import java.util.Optional;

import com.policlinico.smartsalud.domain.entity.User;

import java.util.List;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void deleteById(Long id);
}