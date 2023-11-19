package com.prog4.libraryManagement.controller;


import com.prog4.libraryManagement.model.User;
import com.prog4.libraryManagement.service.SubscribeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscribes")
@AllArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;
    @GetMapping
    public List<User> getAllUsers() {
        return subscribeService.getAllUser();
    }

    @PostMapping("/saveAll")
    public List<User> saveAllUsers(@RequestBody List<User> users) {
        return subscribeService.saveAllUsers(users);
    }

    @PostMapping("/save")
    public User saveBook(@RequestBody User user) {
        return subscribeService.saveUser(user);
    }

    @DeleteMapping("/delete")
    public void deleteBook(@RequestBody User user) {
        subscribeService.deleteUser(user);
    }
}

