package com.priselkov.rest_sample.service;

import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.repository.UserRepository;
import com.priselkov.rest_sample.response.BasicResponse;
import com.priselkov.rest_sample.util.UserValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new LinkedList<>();
        userRepository.findAll().forEach(allUsers::add);
        return allUsers;
    }

    @Override
    public User getUserWithRoles(String login) {
        return userRepository.findById(login).orElse(null);
    }

    @Override
    public BasicResponse deleteUser(String login) {
        if (login != null && userRepository.findById(login).isPresent()) {
            userRepository.deleteById(login);
            return new BasicResponse(true, null);
        }
        return new BasicResponse(false, Collections.singletonList("User doesn't exist"));
    }

    @Override
    public BasicResponse addUserWithRoles(User newUser) {
        if (UserValidator.getDescription(newUser) == null) {
            userRepository.save(newUser);
            return new BasicResponse(true, null);
        } else {
            return new BasicResponse(false, UserValidator.getDescription(newUser));
        }
    }

    @Override
    public BasicResponse updateUser(String userLogin, User user) {
        if (userRepository.findById(userLogin).isPresent()) {
            if (UserValidator.getDescription(user) == null) {
                userRepository.delete(userRepository.findById(userLogin).get());
                userRepository.save(user);
                return new BasicResponse(true, null);
            } else return new BasicResponse(false, UserValidator.getDescription(user));
        }
        return new BasicResponse(false, Collections.singletonList("User doesn't exist"));
    }
}
