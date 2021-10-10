package com.priselkov.rest_sample.service;

import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.repository.UserRepository;
import com.priselkov.rest_sample.response.BasicResponse;
import org.springframework.stereotype.Service;

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
        userRepository.deleteById(login);
        return new BasicResponse(true, "User " + login + "deleted");
    }

    @Override
    public BasicResponse addUserWithRoles(User newUser) {
        userRepository.save(newUser);
        return new BasicResponse(true, "User " + newUser.getLogin() + "deleted");
    }

    @Override
    public BasicResponse updateUser(User user) {
        if (userRepository.findById(user.getLogin()).isPresent()) {
            userRepository.delete(userRepository.findById(user.getLogin()).get());
            userRepository.save(user);
            return new BasicResponse(true, "User " + user.getLogin() + "updated");
        }
        return new BasicResponse(false, "User doesn't exist");
    }
}
