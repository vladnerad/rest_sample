package com.priselkov.rest_sample.service;

import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.repository.UserRepository;
import com.priselkov.rest_sample.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserWithRoles(String login) {
        return null;
    }

    @Override
    public BasicResponse deleteUser(String login) {
        return null;
    }

    @Override
    public BasicResponse addUserWithRoles(User newUser) {
        return null;
    }

    @Override
    public BasicResponse updateUser(User user) {
        return null;
    }
}
