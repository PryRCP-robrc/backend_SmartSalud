package com.policlinico.smartsalud.application.ports.input;

import com.policlinico.smartsalud.domain.model.User;
import java.util.List;

public interface UserUseCase {
    User registerUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}