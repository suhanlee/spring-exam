package com.devsh.itracker.controller;

import com.devsh.itracker.model.User;
import com.devsh.itracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UsersRestController {
    @Autowired
    UserService userService;

    // index
    @RequestMapping(method = RequestMethod.GET)
    List<User> getUsers() {
        List<User> users = userService.findAll();
        return users;
    }

    // show
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    User getUser(@PathVariable Long id) {
        User user = userService.findOne(id);
        return user;
    }

    // create
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<User> postUser(@RequestBody User user, UriComponentsBuilder uriBuilder) {
        User created = userService.create(user);
        URI location = uriBuilder.path("api/users/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    // put
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    User putUser(@PathVariable Long id, @RequestBody User user) {
        return userService.update(user);
    }

    // destroy
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
