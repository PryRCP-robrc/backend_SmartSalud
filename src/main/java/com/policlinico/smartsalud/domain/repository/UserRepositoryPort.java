package com.policlinico.smartsalud.domain.ports.output;

import com.policlinico.smartsalud.domain.model.User;
import java.util.Optional;
import java.util.List;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);
}