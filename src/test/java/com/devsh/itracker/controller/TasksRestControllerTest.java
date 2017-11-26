package com.devsh.itracker.controller;

import com.devsh.itracker.TestUtil;
import com.devsh.itracker.model.Project;
import com.devsh.itracker.model.Task;
import com.devsh.itracker.service.ProjectService;
import com.devsh.itracker.service.TaskService;
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

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TasksRestControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private TasksRestController controller;

    @Mock
    private TaskService service;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void testGetTasks() throws Exception {
        Task task1 = new Task();
        task1.setSubject("subject1");
        task1.setDescription("description");

        Task task2 = new Task();
        task2.setSubject("subject1");
        task2.setDescription("description");

        List<Task> tasks = Arrays.asList(
                task1, task2
        );

        when(service.findAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verify(service).findAll();
    }

    @Test
    public void testGetTaskWithId() throws Exception {
        Task task1 = new Task();
        task1.setSubject("subject1");
        task1.setDescription("description");

        Task task2 = new Task();
        task2.setSubject("subject1");
        task2.setDescription("description");

        List<Task> tasks = Arrays.asList(
                task1, task2
        );

        when(service.findOne(1L)).thenReturn(task1);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verify(service).findOne(1L);
    }
}
