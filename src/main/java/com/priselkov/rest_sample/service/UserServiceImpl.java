package com.priselkov.rest_sample.service;

import com.priselkov.rest_sample.model.Role;
import com.priselkov.rest_sample.model.RoleArray;
import com.priselkov.rest_sample.model.RoleName;
import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.repository.RoleRepository;
import com.priselkov.rest_sample.repository.UserRepository;
import com.priselkov.rest_sample.response.BasicResponse;
import com.priselkov.rest_sample.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<User> allUsers = new LinkedList<>();
        userRepository.findAll().forEach(allUsers::add);
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

            List<Role> roles = new ArrayList<>();
            if (newUser.getRoles() != null && !newUser.getRoles().isEmpty()) {
                for (Role role : newUser.getRoles()) {
                    roles.add(roleRepository.findByName(role.getName()).orElse(null));
                }
            } else {
                roles.add(new Role(RoleName.ROLE_USER));
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
                userRepository.delete(userRepository.findById(userLogin).get());
                userRepository.save(user);
                return new BasicResponse(true, null);
            } else return new BasicResponse(false, UserValidator.getDescription(user));
        }
        return new BasicResponse(false, Collections.singletonList("User doesn't exist"));
    }

    @Override
    public BasicResponse updateUserRoles(String userLogin, RoleArray roles) {
        if (userRepository.findById(userLogin).isPresent()) {
            User user = userRepository.findById(userLogin).orElse(null);
            if (user != null) {
                List<Role> roleList = new ArrayList<>();
                Arrays.asList(roles.getRoles()).forEach(r -> roleList.add(new Role(r)));
                user.setRoles(roleList);
                updateUser(userLogin, user);
                return new BasicResponse(true, null);
            }
        }
        return new BasicResponse(false, Collections.singletonList("User doesn't exist"));
    }
}
