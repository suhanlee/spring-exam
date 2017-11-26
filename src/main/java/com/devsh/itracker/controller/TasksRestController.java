package com.devsh.itracker.controller;

import com.devsh.itracker.model.Task;
import com.devsh.itracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TasksRestController {
    @Autowired
    TaskService taskService;

    // index
    @RequestMapping(method = RequestMethod.GET)
    List<Task> getTasks() {
        List<Task> projects = taskService.findAll();
        return projects;
    }

    // show
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    Task getTask(@PathVariable Long id) {
        Task task = taskService.findOne(id);
        return task;
    }

    // create
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Task> postTask(@RequestBody Task task, UriComponentsBuilder uriBuilder) {
        Task created = taskService.create(task);
        URI location = uriBuilder.path("api/tasks/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    // put
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    Task putTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.update(task);
    }

    // destroy
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyTask(@PathVariable Long id) {
        taskService.delete(id);
    }
}
