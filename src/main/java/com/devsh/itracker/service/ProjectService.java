package com.devsh.itracker.service;

import com.devsh.itracker.dao.ProjectRepository;
import com.devsh.itracker.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findOne(Long id) {
        return projectRepository.findOne(id);
    }

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public Project update(Project project) {
        return projectRepository.save(project);
    }

    public void delete(Long id) {
        projectRepository.delete(id);
    }

}
