package com.priselkov.rest_sample;

import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.repository.UserRepository;
import com.priselkov.rest_sample.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceLayerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    public static final String userLogin = "testLogin";
    private final User user = new User(userLogin, "testPass", "testName");

    @Test
    public void addUserTest() {
        userService.addUserWithRoles(user);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void deleteUserTest() {
        userService.addUserWithRoles(user);
        userService.deleteUser(userLogin);
        Mockito.verify(userRepository).deleteById(userLogin);
    }

    @Test
    public void updateUserTest() {

        String userLogin = "testLogin";
        User userToUpd = new User(userLogin, "testPass", "testName");
        User userNotExisted = new User(userLogin + "n/e", "random", "random");

        userService.addUserWithRoles(userToUpd);

        userToUpd.setPass("newPass");
        userToUpd.setName("newName");

        userService.updateUser(userToUpd.getLogin(), userToUpd);
        userService.updateUser(userNotExisted.getLogin(), userNotExisted);

        Mockito.verify(userRepository).save(userToUpd);
        Mockito.verify(userRepository, Mockito.never()).save(userNotExisted);
    }
}
