package com.example.user.services;

import com.example.user.repositories.UserRepository;
import org.example.core.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //CREATE
    public User createUser(User user) {
        return userRepository.save(user);
    }

    //READ
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    //UPDATE
    public User setUser(int id, User user) {
        User u = userRepository.findById(id).get();
        u.setFirstname(user.getFirstname());
        u.setLastname(user.getLastname());
        u = userRepository.save(u);
        return u;
    }

    //DELETE
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}