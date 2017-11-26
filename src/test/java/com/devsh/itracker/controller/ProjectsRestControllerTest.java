package com.devsh.itracker.controller;

import com.devsh.itracker.TestUtil;
import com.devsh.itracker.model.Project;
import com.devsh.itracker.service.ProjectService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProjectsRestControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private ProjectsRestController controller;

    @Mock
    private ProjectService service;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void testGetProjects() throws Exception {
        Project project1 = new Project();
        project1.setName("test1");

        Project project2 = new Project();
        project2.setName("test1");

        List<Project> projects = Arrays.asList(
                project1, project2
        );

        when(service.findAll()).thenReturn(projects);

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verify(service).findAll();
    }

    @Test
    public void testGetProjectWithId() throws Exception {
        Project project1 = new Project();
        project1.setName("test1");

        Project project2 = new Project();
        project2.setName("test1");

        List<Project> projects = Arrays.asList(
                project1, project2
        );

        when(service.findOne(1L)).thenReturn(project1);

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk());

        verify(service).findOne(1L);
    }

    @Test
    public void testPostProject() throws Exception {
//        Project project2 = new Project();
//        project2.setId(1L);
//        project2.setName("test1");
//
//        Gson gson = new Gson();
//        String json = gson.toJson(project2);
//
//        when(service.create(project2)).thenReturn(project2);
//
//        mockMvc.perform(post("/api/projects")
//                .contentType(MediaType.APPLICATION_JSON).content(json))
//                .andExpect(status().isCreated())
//                .andExpect(content()
//                        .string(equalTo(json))).andReturn();
//
//        verify(service).create(project2);
    }



}
