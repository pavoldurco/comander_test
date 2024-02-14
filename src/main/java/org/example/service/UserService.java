package org.example.service;

import org.example.domain.User;
import org.example.exceptions.DatabaseUnavailableException;
import org.example.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) throws DatabaseUnavailableException {
        userRepository.addUser(user);
    }

    public void deleteAllUsers() throws DatabaseUnavailableException {
        userRepository.deleteAllUsers();
    }

    public void printAllUsers() throws DatabaseUnavailableException {
        userRepository.printAllUsers();
    }
}