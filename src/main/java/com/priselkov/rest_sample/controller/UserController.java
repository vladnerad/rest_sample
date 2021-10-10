package com.priselkov.rest_sample.controller;

import com.priselkov.rest_sample.repository.UserRepository;
import com.priselkov.rest_sample.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = new LinkedList<>();
        userRepository.findAll().forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userlogin}")
    public ResponseEntity<User> test(@PathVariable String userlogin) {
        return new ResponseEntity<>(userRepository.findById(userlogin).orElse(new User("test", "test", "test")), HttpStatus.OK);
    }

}
