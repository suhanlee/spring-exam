package com.devsh.itracker.integration;

import com.devsh.Application;
import com.devsh.itracker.dao.TaskRepository;
import com.devsh.itracker.model.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port:0",
        "spring.datasource.url:jdbc:h2:mem:itracker-test;DB_CLOSE_ON_EXIT=FALSE"})
public class TasksRestControllerIntegrationTest {
    @Autowired
    TaskRepository taskRepository;

    @Value("${local.server.port}")
    int port;

    String apiEndpoint;
    RestTemplate restTemplate = new TestRestTemplate();
    Task task1;
    Task task2;

    @Before
    public void setUp() {
        taskRepository.deleteAll();
        task1 = new Task();
        task1.setSubject("subject");
        task1.setDescription("description");
        task2 = new Task();
        task2.setSubject("kyle");
        task2.setDescription("lee");

        taskRepository.save(Arrays.asList(task1, task2));
        apiEndpoint = "http://localhost:" + port + "/api/tasks";
    }

    @Test
    public void testGetProjects() throws Exception {
        ResponseEntity<List<Task>> response = restTemplate.exchange(
                apiEndpoint, HttpMethod.GET, null /* body, header */,
                new ParameterizedTypeReference<List<Task>>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Task t1 = response.getBody().get(0);

        assertThat(t1.getId(), is(task1.getId()));
        assertThat(t1.getSubject(), is(task1.getSubject()));
        assertThat(t1.getDescription(), is(task1.getDescription()));

        Task t2 = response.getBody().get(1);

        assertThat(t2.getId(), is(task2.getId()));
        assertThat(t2.getSubject(), is(task2.getSubject()));
        assertThat(t2.getDescription(), is(task2.getDescription()));
    }

    @Test
    public void testPostProject() throws Exception {
        Task task = new Task();
        task.setSubject("task put A");

        ResponseEntity<Task> response = restTemplate.exchange(
                apiEndpoint, HttpMethod.POST, new HttpEntity<>(task), Task.class,
                new ParameterizedTypeReference<Task>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        Task projectResult = response.getBody();
        assertThat(projectResult.getId(), is(notNullValue()));
        assertThat(projectResult.getSubject(), is(task.getSubject()));
        assertThat(projectResult.getDescription(), is(task.getDescription()));
    }

    @Test
    public void testPutProject() throws Exception {
        task1.setSubject("modify task subject");

        ResponseEntity<Task> response = restTemplate.exchange(
                apiEndpoint + "/{id}", HttpMethod.PUT, new HttpEntity<>(task1), Task.class,
                Collections.singletonMap("id", task1.getId()));

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Task userResult = response.getBody();
        assertThat(userResult.getId(), is(notNullValue()));
        assertThat(userResult.getSubject(), is(task1.getSubject()));
        assertThat(userResult.getDescription(), is(task1.getDescription()));

    }

    @Test
    public void testDestroyProject() throws Exception {
        ResponseEntity<Void> response = restTemplate.exchange(
                apiEndpoint + "/{id}", HttpMethod.DELETE, null, Void.class,
                Collections.singletonMap("id", task1.getId()));

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }
}
