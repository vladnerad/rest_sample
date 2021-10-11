package com.priselkov.rest_sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priselkov.rest_sample.controller.UserController;
import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.response.BasicResponse;
import com.priselkov.rest_sample.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void addUser(){
        User user = new User("testLogin", "testPass", "testName");
        Mockito.when(userService.addUserWithRoles(Mockito.any())).thenReturn(new BasicResponse(true, "User " + user.getLogin() + " created"));

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/users")
                            .content(new ObjectMapper().writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isCreated())
                    .andExpect(content().json(new ObjectMapper().writeValueAsString(new BasicResponse(true, "User " + user.getLogin() + " created"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
