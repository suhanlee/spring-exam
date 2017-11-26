package com.devsh.itracker.integration;

import com.devsh.Application;
import com.devsh.itracker.dao.UserRepository;
import com.devsh.itracker.model.User;
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
public class UserRestControllerIntegrationTest {
    @Autowired
    UserRepository userRepository;

    @Value("${local.server.port}")
    int port;

    String apiEndpoint;
    RestTemplate restTemplate = new TestRestTemplate();
    User user1;
    User user2;

    @Before
    public void setUp() {
        userRepository.deleteAll();
        user1 = new User();
        user1.setUsername("suhan");
        user1.setPassword("lee");
        user2 = new User();
        user2.setUsername("kyle");
        user2.setPassword("lee");

        userRepository.save(Arrays.asList(user1, user2));
        apiEndpoint = "http://localhost:" + port + "/api/users";
    }

    @Test
    public void testGetProjects() throws Exception {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                apiEndpoint, HttpMethod.GET, null /* body, header */,
                new ParameterizedTypeReference<List<User>>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        User p1 = response.getBody().get(0);

        assertThat(p1.getId(), is(user1.getId()));
        assertThat(p1.getUsername(), is(user1.getUsername()));

        User p2 = response.getBody().get(1);

        assertThat(p2.getId(), is(user2.getId()));
        assertThat(p2.getUsername(), is(user2.getUsername()));
    }

    @Test
    public void testPostProject() throws Exception {
        User user = new User();
        user.setUsername("user A");

        ResponseEntity<User> response = restTemplate.exchange(
                apiEndpoint, HttpMethod.POST, new HttpEntity<>(user), User.class,
                new ParameterizedTypeReference<User>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        User projectResult = response.getBody();
        assertThat(projectResult.getId(), is(notNullValue()));
        assertThat(projectResult.getUsername(), is(user.getUsername()));
    }

    @Test
    public void testPutProject() throws Exception {
        user1.setUsername("modify user");

        ResponseEntity<User> response = restTemplate.exchange(
                apiEndpoint + "/{id}", HttpMethod.PUT, new HttpEntity<>(user1), User.class,
                Collections.singletonMap("id", user1.getId()));

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        User userResult = response.getBody();
        assertThat(userResult.getId(), is(notNullValue()));
        assertThat(userResult.getUsername(), is(user1.getUsername()));
    }

    @Test
    public void testDestroyProject() throws Exception {
        ResponseEntity<Void> response = restTemplate.exchange(
                apiEndpoint + "/{id}", HttpMethod.DELETE, null, Void.class,
                Collections.singletonMap("id", user1.getId()));

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }
}
