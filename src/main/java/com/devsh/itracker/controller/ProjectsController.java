package com.devsh.itracker.controller;

import com.devsh.itracker.model.Project;
import com.devsh.itracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProjectsController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(path = "/projects", method = RequestMethod.GET)
    public String index(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "projects/index";
    }

    // new
    @RequestMapping(path = "/projects/new", method = RequestMethod.GET)
    public String actionNew(Model model) {
        if(!model.containsAttribute("project")) {
            model.addAttribute("project", new Project());
        }
        model.addAttribute("action", "/projects");
        model.addAttribute("method", "post");
        model.addAttribute("submit", "Add");

        return "projects/form";
    }

    // edit
    @RequestMapping(path = "/projects/{id}/edit", method = RequestMethod.GET)
    public String actionEdit(@PathVariable Long id,  Model model) {
        if(!model.containsAttribute("project")) {
            model.addAttribute("project", projectService.findOne(id));
        }
        model.addAttribute("action", String.format("/projects/%s", id ));
        model.addAttribute("method", "put");
        model.addAttribute("submit", "Update");
        return "projects/form";
    }

    private String processUpdateLogic(Project project, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            redirectAttributes.addFlashAttribute("project", project);

            return String.format("redirect:/projects/%s/edit", project.getId());
        }

        projectService.update(project);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Project successfully updated!", FlashMessage.Status.SUCCESS));
        return "redirect:/projects";
    }

    // update (partial)
    @RequestMapping(path = "/projects/{id}", method = RequestMethod.PATCH)
    public String actionUpdatePart(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
        return processUpdateLogic(project, result, redirectAttributes);
    }


    // update (total)
    @RequestMapping(path = "/projects/{id}", method = RequestMethod.PUT)
    public String actionUpdateTotal(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
        return processUpdateLogic(project, result, redirectAttributes);
    }

    // create
    @RequestMapping(path = "/projects", method = RequestMethod.POST)
    public String actionCreate(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);

            redirectAttributes.addFlashAttribute("project", project);
            return "redirect:/projects/new";
        }

        projectService.create(project);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project successfully added!", FlashMessage.Status.SUCCESS));

        return "redirect:/projects";
    }

    // show
    @RequestMapping(path = "/projects/{id}", method = RequestMethod.GET)
    public String actionShow(@PathVariable Long id, Model model) {
        Project project = projectService.findOne(id);
        model.addAttribute("project", project);
        return "projects/show";
    }


    // destroy
    @RequestMapping(path = "/projects/{id}", method = RequestMethod.DELETE)
    public String actionDestroy(@PathVariable Long id,  RedirectAttributes redirectAttributes) {
        projectService.delete(id);
        return "redirect:/projects";
    }

}
