package com.priselkov.rest_sample.service;

import com.priselkov.rest_sample.model.Role;
import com.priselkov.rest_sample.model.RoleName;
import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.repository.RoleRepository;
import com.priselkov.rest_sample.repository.UserRepository;
import com.priselkov.rest_sample.response.BasicResponse;
import com.priselkov.rest_sample.util.UserValidator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new LinkedList<>(userRepository.findAll());
        allUsers.forEach(user -> user.setRoles(null));
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
            if (userRepository.findById(newUser.getLogin()).isPresent()) {
                return new BasicResponse(false, Collections.singletonList("User with this login already exists"));
            }
            List<Role> roles = new ArrayList<>();
            if (newUser.getRoles() != null && !newUser.getRoles().isEmpty()) {
                for (Role role : newUser.getRoles()) {
                    roles.add(roleRepository.findByName(role.getName()).orElse(null));
                }
            } else {
                roles.add(roleRepository.findByName(RoleName.ROLE_USER).orElse(null));
            }
            newUser.setRoles(roles);
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
                User oldUser = userRepository.findById(userLogin).get();
                oldUser.setPass(user.getPass());
                oldUser.setName(user.getName());
                if (user.getRoles() == null || user.getRoles().isEmpty()){
                    user.setRoles(Collections.singletonList(roleRepository.findByName(RoleName.ROLE_USER).orElse(null)));
                } else {
                    List<Role> roles = new ArrayList<>();
                    for (Role r: user.getRoles()){
                        roles.add(roleRepository.findByName(r.getName()).orElseThrow(IllegalArgumentException::new));
                    }
                    oldUser.setRoles(roles);
                }
                userRepository.save(oldUser);
                return new BasicResponse(true, null);
            } else return new BasicResponse(false, UserValidator.getDescription(user));
        }
        return new BasicResponse(false, Collections.singletonList("User doesn't exist"));
    }

    @Override
    public BasicResponse updateUserRoles(String userLogin, User updUser) {
        if (userRepository.findById(userLogin).isPresent()) {
            User user = userRepository.findById(userLogin).orElse(null);
            if (user != null) {
                List<Role> newRoles = updUser.getRoles();
                if (newRoles != null && !newRoles.isEmpty()) {
                    user.setRoles(newRoles);
                    updateUser(userLogin, user);
                    return new BasicResponse(true, null);
                } else return new BasicResponse(false, Collections.singletonList("User roles array is empty"));
            }
        }
        return new BasicResponse(false, Collections.singletonList("User doesn't exist"));
    }
}
