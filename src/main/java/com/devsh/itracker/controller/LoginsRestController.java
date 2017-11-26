package com.devsh.itracker.controller;

import com.devsh.itracker.model.User;
import com.devsh.itracker.service.UserService;
import com.devsh.itracker.service.exception.UserNameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/users/login")
public class LoginsRestController {
    @Autowired
    UserService userService;

    // create
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<User> postLogin(@RequestBody User user, UriComponentsBuilder uriBuilder) {

        User findUser = userService.findByUsername(user.getUsername());
        if (findUser == null) {
            throw new UserNameNotFoundException();
        }

        if (!findUser.getPassword().equals(user.getPassword())) {
            throw new UserNameNotFoundException();
        }

        findUser.setUuid(UUID.randomUUID().toString());
        userService.update(findUser);

        URI location = uriBuilder.path("api/tasks/{id}")
                .buildAndExpand(findUser.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(findUser, headers, HttpStatus.CREATED);
    }
}
