package com.priselkov.rest_sample;

import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.repository.UserRepository;
import com.priselkov.rest_sample.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserServiceLayerTest {

    @Mock
    private UserRepository userRepository;

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

//    @Test
//    public void updateUser() {
//
//        String userLogin = "testLogin";
//        User userToUpd = new User(userLogin, "testPass", "testName");
//        User userNotExisted = new User(userLogin + "n/e", "random", "random");
//
//        userService.addUserWithRoles(userToUpd);
//
//        userToUpd.setPass("newPass");
//        userToUpd.setName("newName");
//
//        userService.updateUser(userToUpd.getLogin(), userToUpd);
//        userService.updateUser(userNotExisted.getLogin(), userNotExisted);
//
//        Mockito.verify(userRepository).save(userToUpd);
//        Mockito.verify(userRepository, Mockito.never()).save(userNotExisted);
//    }

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
        userService.updateUser(user.getLogin(), user);
        Mockito.verify(userRepository).delete(user);
        Mockito.verify(userRepository).save(user);
    }

//    @Test
//    public void updateUserRoles_invalid() {
//
//        User user = new User("testLogin", "validPass1", "testName");
//
//        List<String> userRoles = new ArrayList<>();
//        userRoles.add("ROLE_OPERATOR");
//        userRoles.add("ROLE_ANALYTIC");
//
//        userService.updateUser(user.getLogin(), user);
//
//        Mockito.verify(userRepository).save(userToUpd);
//        Mockito.verify(userRepository, Mockito.never()).save(userNotExisted);
//    }
}
