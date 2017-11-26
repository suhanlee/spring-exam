package com.devsh.itracker.controller;

import com.devsh.itracker.model.Project;
import com.devsh.itracker.service.ProjectService;
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
@RequestMapping("api/projects")
public class ProjectsRestController {

    @Autowired
    ProjectService projectService;

    // index
    @RequestMapping(method = RequestMethod.GET)
    List<Project> getProjects() {
       List<Project> projects = projectService.findAll();
       return projects;
    }

    // show
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    Project getProject(@PathVariable Long id) {
        Project project = projectService.findOne(id);
        return project;
    }

    // create
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Project> postProject(@RequestBody Project project, UriComponentsBuilder uriBuilder) {
        Project created = projectService.create(project);
        URI location = uriBuilder.path("api/projects/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    // put
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    Project putProject(@PathVariable Long id, @RequestBody Project project) {
        return projectService.update(project);
    }

    // destroy
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyProject(@PathVariable Long id) {
        projectService.delete(id);
    }

}
