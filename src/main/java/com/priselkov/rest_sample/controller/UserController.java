package com.priselkov.rest_sample.controller;

import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.response.BasicResponse;
import com.priselkov.rest_sample.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userlogin}")
    public ResponseEntity<User> getUser(@PathVariable String userlogin) {
        return new ResponseEntity<>(userService.getUserWithRoles(userlogin), HttpStatus.OK);
    }

    public ResponseEntity<BasicResponse> deleteUser(String userlogin) {
        return new ResponseEntity<>(userService.deleteUser(userlogin), HttpStatus.OK);
    }

    public ResponseEntity<BasicResponse> addUser(User newUser) {
        return new ResponseEntity<>(userService.addUserWithRoles(newUser), HttpStatus.OK);
    }

    public ResponseEntity<BasicResponse> updateUser(User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }
}
