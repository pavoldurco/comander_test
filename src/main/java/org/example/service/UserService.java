package org.example.service;

import org.example.domain.User;
import org.example.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAllUsers();
    }

    public void printAllUsers() {
        userRepository.printAllUsers();
    }
}