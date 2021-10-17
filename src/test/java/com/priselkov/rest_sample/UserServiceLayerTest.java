package com.priselkov.rest_sample;

import com.priselkov.rest_sample.model.Role;
import com.priselkov.rest_sample.model.RoleName;
import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.repository.RoleRepository;
import com.priselkov.rest_sample.repository.UserRepository;
import com.priselkov.rest_sample.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceLayerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void addUser_notValid() {
        User user = new User("testLogin", "invalidPass", "testName");
        userService.addUserWithRoles(user);
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    public void addUser_success() {
        User user = new User("testLogin", "validPass1", "testName");
        userService.addUserWithRoles(user);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void deleteUser_success() {
        User user = new User("testLogin", "validPass1", "testName");
        Mockito.when(userRepository.findById(ArgumentMatchers.eq(user.getLogin()))).thenReturn(Optional.of(user));
        userService.deleteUser(user.getLogin());
        Mockito.verify(userRepository).deleteById(user.getLogin());
    }

    @Test
    public void deleteUser_notExists() {
        User user = new User("testLogin", "validPass1", "testName");
        userService.deleteUser(user.getLogin());
        Mockito.verify(userRepository, Mockito.never()).deleteById(user.getLogin());
    }

    @Test
    public void updateUser_invalid() {
        User user = new User("testLogin", "invalidPass", "testName");
        userService.updateUser(user.getLogin(), user);
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    public void updateUser_success() {
        User user = new User("testLogin", "validPass1", "testName");
        Mockito.when(userRepository.findById(ArgumentMatchers.eq(user.getLogin()))).thenReturn(Optional.of(user));
        Mockito.when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(new Role(RoleName.ROLE_USER)));
        userService.updateUser(user.getLogin(), user);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void updateUserRoles_invalid() {
        User user = new User();
        List<Role> userRoles = new ArrayList<>();
        try {
            userRoles.add(roleRepository.findByName(RoleName.valueOf("ROLE_DOESNT_EXIST")).orElse(null));
            userRoles.add(roleRepository.findByName(RoleName.ROLE_ANALYTIC).orElse(null));
        } catch (IllegalArgumentException ignored){}

        user.setRoles(userRoles);
        userService.updateUserRoles(user.getLogin(), user);
        Mockito.verify(userRepository, Mockito.never()).save(ArgumentMatchers.any(User.class));
        Mockito.verify(userRepository, Mockito.never()).delete(ArgumentMatchers.any(User.class));
    }

    @Test
    public void updateUserRoles_valid() {
        User user = new User("testLogin", "validPass1", "testName");

        Mockito.when(userRepository.findById(ArgumentMatchers.eq(user.getLogin()))).thenReturn(Optional.of(user));
        Mockito.when(roleRepository.findByName(Mockito.any())).thenReturn(Optional.of(new Role(RoleName.ROLE_USER)));

        List<Role> userRoles = new ArrayList<>();
        try {
            userRoles.add(new Role(RoleName.ROLE_ANALYTIC));
        } catch (IllegalArgumentException ignored){}
        user.setRoles(userRoles);

        userService.updateUserRoles(user.getLogin(), user);

        Mockito.verify(userRepository).save(ArgumentMatchers.any(User.class));
    }
}
