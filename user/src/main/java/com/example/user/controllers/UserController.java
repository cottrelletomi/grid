package com.example.user.controllers;

import com.example.user.services.UserService;
import org.example.core.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    //CREATE
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    //READ
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @GetMapping("/search")
    public Optional<User> getUserByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email);
    }

    //UPDATE
    @PutMapping("/{id}")
    public User setUser(@PathVariable("id") int id, @RequestBody User user) {
        return userService.setUser(id, user);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public void deletetUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
    }
}