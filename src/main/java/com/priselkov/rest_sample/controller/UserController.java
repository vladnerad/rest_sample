package com.priselkov.rest_sample.controller;

import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.response.BasicResponse;
import com.priselkov.rest_sample.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
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

    @DeleteMapping("/{userlogin}")
    public ResponseEntity<BasicResponse> deleteUser(@PathVariable String userlogin) {
        BasicResponse basicResponse = userService.deleteUser(userlogin);
        if (basicResponse.getSuccess()) {
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(basicResponse, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<BasicResponse> addUser(@RequestBody User newUser) {
        return new ResponseEntity<>(userService.addUserWithRoles(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/{userlogin}")
    public ResponseEntity<BasicResponse> updateUser(@PathVariable String userlogin, @RequestBody User user) {
        BasicResponse basicResponse = userService.updateUser(userlogin, user);
        if (basicResponse.getSuccess())
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        else
            return new ResponseEntity<>(basicResponse, HttpStatus.NO_CONTENT);
    }
}
