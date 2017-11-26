package com.devsh.itracker.integration;

import com.devsh.Application;
import com.devsh.itracker.dao.ProjectRepository;
import com.devsh.itracker.model.Project;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
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
public class ProjectRestControllerIntegrationTest {
    @Autowired
    ProjectRepository projectRepository;

    @Value("${local.server.port}")
    int port;

    String apiEndpoint;
    RestTemplate restTemplate = new TestRestTemplate();
    Project project1;
    Project project2;

    @Before
    public void setUp() {
        projectRepository.deleteAll();
        project1 = new Project();
        project1.setName("Project1");
        project2 = new Project();
        project2.setName("Project2");

        projectRepository.save(Arrays.asList(project1, project2));
        apiEndpoint = "http://localhost:" + port + "/api/projects";
    }

    @Test
    public void testGetProjects() throws Exception {
        ResponseEntity<List<Project>> response = restTemplate.exchange(
                apiEndpoint, HttpMethod.GET, null /* body, header */,
                new ParameterizedTypeReference<List<Project>>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Project p1 = response.getBody().get(0);

        assertThat(p1.getId(), is(project1.getId()));
        assertThat(p1.getName(), is(project1.getName()));

        Project p2 = response.getBody().get(1);

        assertThat(p2.getId(), is(project2.getId()));
        assertThat(p2.getName(), is(project2.getName()));
    }

    @Test
    public void testPostProject() throws Exception {
        Project project = new Project();
        project.setName("Project A");

        ResponseEntity<Project> response = restTemplate.exchange(
                apiEndpoint, HttpMethod.POST, new HttpEntity<>(project), Project.class,
                new ParameterizedTypeReference<Project>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        Project projectResult = response.getBody();
        assertThat(projectResult.getId(), is(notNullValue()));
        assertThat(projectResult.getName(), is(project.getName()));
    }

    @Test
    public void testPutProject() throws Exception {
        project1.setName("modify project");

        ResponseEntity<Project> response = restTemplate.exchange(
                apiEndpoint + "/{id}", HttpMethod.PUT, new HttpEntity<>(project1), Project.class,
               Collections.singletonMap("id", project1.getId()));

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Project projectResult = response.getBody();
        assertThat(projectResult.getId(), is(notNullValue()));
        assertThat(projectResult.getName(), is(project1.getName()));
    }

    @Test
    public void testDestroyProject() throws Exception {
        ResponseEntity<Void> response = restTemplate.exchange(
                apiEndpoint + "/{id}", HttpMethod.DELETE, null, Void.class,
                Collections.singletonMap("id", project1.getId()));

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }
}
