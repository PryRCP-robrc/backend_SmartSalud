package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.application.ports.input.UserUseCase;
import com.policlinico.smartsalud.domain.model.User;
import com.policlinico.smartsalud.domain.ports.output.UserRepositoryPort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements UserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User registerUser(User user) {
        if(userRepositoryPort.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }
        user.setActive(true); 
        return userRepositoryPort.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositoryPort.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        return userRepositoryPort.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = getUserById(id);
        userRepositoryPort.deleteById(existingUser.getId());
    }
}