package com.priselkov.rest_sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priselkov.rest_sample.controller.UserController;
import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.response.BasicResponse;
import com.priselkov.rest_sample.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void addUser() {
        User user = new User("testLogin", "testPass", "testName");
        Mockito.when(userService.addUserWithRoles(Mockito.any())).thenReturn(new BasicResponse(true, null));

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/users")
                            .content(new ObjectMapper().writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isCreated())
                    .andExpect(content().json(new ObjectMapper().writeValueAsString(new BasicResponse(true, null))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateUser_success() {
        User user = new User("testLogin", "testPass", "testName");
        User updated = new User("testLogin", "updated", "updated");
        BasicResponse basicResponse = new BasicResponse(true, null);

        userService.addUserWithRoles(user);
        Mockito.when(userService.updateUser(ArgumentMatchers.eq(updated.getLogin()), ArgumentMatchers.any(User.class))).thenReturn(basicResponse);

        ObjectMapper mapper = new ObjectMapper();

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.put("/api/users/" + user.getLogin())
                            .content(mapper.writeValueAsString(updated))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(basicResponse)));

            Mockito.verify(userService, Mockito.times(1)).updateUser(ArgumentMatchers.eq(updated.getLogin()), ArgumentMatchers.any(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateUser_notFound() {

        User updated = new User("not_existed", "updated", "updated");
        BasicResponse basicResponse = new BasicResponse(false, Collections.singletonList("User doesn't exist"));
        ObjectMapper mapper = new ObjectMapper();
        Mockito.when(userService.updateUser(ArgumentMatchers.eq(updated.getLogin()), ArgumentMatchers.any(User.class))).thenReturn(basicResponse);

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.put("/api/users/" + updated.getLogin())
                            .content(mapper.writeValueAsString(updated))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isNoContent())
                    .andExpect(content().json(mapper.writeValueAsString(basicResponse)));

        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService, Mockito.times(1)).updateUser(ArgumentMatchers.eq(updated.getLogin()), ArgumentMatchers.any(User.class));
    }
}
