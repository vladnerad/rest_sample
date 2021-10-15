package com.priselkov.rest_sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.response.BasicResponse;
import com.priselkov.rest_sample.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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
    public ResponseEntity<MappingJacksonValue> getAllUsers() {
        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAllExcept("roles");

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("userFilter", simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userService.getAllUsers());
        mappingJacksonValue.setFilters(filterProvider);

        return new ResponseEntity<>(mappingJacksonValue, HttpStatus.OK);
    }

    @GetMapping("/{userlogin}")
    public ResponseEntity<MappingJacksonValue> getUser(@PathVariable String userlogin) {
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("userFilter", SimpleBeanPropertyFilter.serializeAll());
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userService.getUserWithRoles(userlogin));
        mappingJacksonValue.setFilters(filterProvider);

        return new ResponseEntity<>(mappingJacksonValue, HttpStatus.OK);
    }

    @DeleteMapping("/{userlogin}")
    public ResponseEntity<BasicResponse> deleteUser(@PathVariable String userlogin) {
        BasicResponse basicResponse = userService.deleteUser(userlogin);
        if (basicResponse.getSuccess()) {
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
    }
}
