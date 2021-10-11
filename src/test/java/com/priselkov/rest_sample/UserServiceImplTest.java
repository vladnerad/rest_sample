package com.priselkov.rest_sample;

import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.repository.UserRepository;
import com.priselkov.rest_sample.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void addUserTest(){
        User user = new User("testLogin", "testPass", "testName");

//        Mockito.when(userRepository.save(user))
    }
}
