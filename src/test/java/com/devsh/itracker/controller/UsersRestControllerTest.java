package com.devsh.itracker.controller;

import com.devsh.itracker.TestUtil;
import com.devsh.itracker.model.Task;
import com.devsh.itracker.model.User;
import com.devsh.itracker.service.TaskService;
import com.devsh.itracker.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(MockitoJUnitRunner.class)
public class UsersRestControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private UsersRestController controller;

    @Mock
    private UserService service;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void testGetUserList() throws Exception {
        User user1 = new User();
        user1.setUsername("suhan");
        user1.setPassword("lee");

        User user2 = new User();
        user2.setUsername("kyle");
        user2.setPassword("lee");

        List<User> users = Arrays.asList(
                user1, user2
        );

        when(service.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verify(service).findAll();
    }

    @Test
    public void testGetUserWithId() throws Exception {
        User user1 = new User();
        user1.setUsername("suhan");
        user1.setPassword("lee");

        User user2 = new User();
        user2.setUsername("kyle");
        user2.setPassword("lee");

        List<User> users = Arrays.asList(
                user1, user2
        );

        when(service.findOne(1L)).thenReturn(user1);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verify(service).findOne(1L);
    }
}
