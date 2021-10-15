package com.priselkov.rest_sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.priselkov.rest_sample.controller.UserController;
import com.priselkov.rest_sample.model.Role;
import com.priselkov.rest_sample.model.RoleName;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        ObjectMapper mapper = new ObjectMapper();
        FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", SimpleBeanPropertyFilter.serializeAll());
        mapper.setFilterProvider(filters);

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/users")
                            .content(mapper.writeValueAsString(user))
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

        Mockito.when(userService.updateUser(ArgumentMatchers.eq(updated.getLogin()), ArgumentMatchers.any(User.class))).thenReturn(basicResponse);

        ObjectMapper mapper = new ObjectMapper();
        FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", SimpleBeanPropertyFilter.serializeAll());
        mapper.setFilterProvider(filters);

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

//    @Test
//    public void updateUserRoles_success() {
//        User user = new User("testLogin", "testPass", "testName");
//        List<String> userRoles = new ArrayList<>();
//        userRoles.add("ROLE_OPERATOR");
//        userRoles.add("ROLE_ANALYTIC");
//
//        BasicResponse basicResponse = new BasicResponse(true, null);
//
//        Mockito.when(userService.updateUserRoles(user.getLogin(), userRoles)).thenReturn(basicResponse);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            mockMvc.perform(
//                    MockMvcRequestBuilders.post("/api/users/" + user.getLogin())
//                            .content(mapper.writeValueAsString(userRoles))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .accept(MediaType.APPLICATION_JSON)
//            )
//                    .andExpect(status().isOk())
//                    .andExpect(content().json(mapper.writeValueAsString(basicResponse)));
//
//            Mockito.verify(userService, Mockito.times(1)).updateUserRoles(ArgumentMatchers.eq(user.getLogin()), ArgumentMatchers.anyList());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void updateUser_notFound() {

        User updated = new User("not_existed", "updated", "updated");
        BasicResponse basicResponse = new BasicResponse(false, Collections.singletonList("User doesn't exist"));
        ObjectMapper mapper = new ObjectMapper();
        FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", SimpleBeanPropertyFilter.serializeAll());
        mapper.setFilterProvider(filters);
        Mockito.when(userService.updateUser(ArgumentMatchers.eq(updated.getLogin()), ArgumentMatchers.any(User.class))).thenReturn(basicResponse);

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.put("/api/users/" + updated.getLogin())
                            .content(mapper.writeValueAsString(updated))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(mapper.writeValueAsString(basicResponse)));

        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService, Mockito.times(1)).updateUser(ArgumentMatchers.eq(updated.getLogin()), ArgumentMatchers.any(User.class));
    }

    @Test
    public void updateUserRoles_notFound() {

        User updated = new User("not_existed", "updated", "updated");
        List<String> userRoles = new ArrayList<>();
        userRoles.add("ROLE_OPERATOR");
        userRoles.add("ROLE_ANALYTIC");

        BasicResponse basicResponse = new BasicResponse(false, Collections.singletonList("User doesn't exist"));
        ObjectMapper mapper = new ObjectMapper();
//        Mockito.when(userService.updateUserRoles(updated.getLogin(), userRoles)).thenReturn(basicResponse);

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/users/" + updated.getLogin())
                            .content(mapper.writeValueAsString(userRoles))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(mapper.writeValueAsString(basicResponse)));

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Mockito.verify(userService, Mockito.times(1)).updateUserRoles(updated.getLogin(), userRoles);
    }

    @Test
    public void deleteUser_success() {
        User user = new User("testLogin", "testPass", "testName");
        BasicResponse basicResponse = new BasicResponse(true, null);
        Mockito.when(userService.deleteUser(ArgumentMatchers.eq(user.getLogin()))).thenReturn(basicResponse);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.delete("/api/users/" + user.getLogin())
                            .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(basicResponse)));

        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService, Mockito.times(1)).deleteUser(user.getLogin());
    }

    @Test
    public void deleteUser_notExists() {
        User deleted = new User("not_existed", "deleted", "deleted");
        BasicResponse basicResponse = new BasicResponse(false, Collections.singletonList("User doesn't exist"));
        Mockito.when(userService.deleteUser(ArgumentMatchers.eq(deleted.getLogin()))).thenReturn(basicResponse);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.delete("/api/users/" + deleted.getLogin())
                            .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(mapper.writeValueAsString(basicResponse)));

        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.verify(userService, Mockito.times(1)).deleteUser(deleted.getLogin());
    }
}
