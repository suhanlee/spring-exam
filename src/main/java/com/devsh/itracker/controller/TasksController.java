package com.devsh.itracker.controller;

import com.devsh.itracker.model.Task;
import com.devsh.itracker.service.ProjectService;
import com.devsh.itracker.service.TaskService;
import com.devsh.itracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TasksController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @RequestMapping(path = "/tasks", method = RequestMethod.GET)
    public String index(Model model) {
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks/index";
    }

    // new
    @RequestMapping(path = "/tasks/new", method = RequestMethod.GET)
    public String actionNew(Model model) {
        if(!model.containsAttribute("task")) {
            model.addAttribute("task", new Task());
        }

        model.addAttribute("users", userService.findAll());
        model.addAttribute("projects", projectService.findAll());

        model.addAttribute("action", "/tasks");
        model.addAttribute("method", "post");
        model.addAttribute("back", "/tasks");
        model.addAttribute("submit", "Add");

        return "tasks/form";
    }

    // edit
    @RequestMapping(path = "/tasks/{id}/edit", method = RequestMethod.GET)
    public String actionEdit(@PathVariable Long id, Model model) {
        if(!model.containsAttribute("task")) {
            model.addAttribute("task", taskService.findOne(id));
        }

        model.addAttribute("users", userService.findAll());
        model.addAttribute("projects", projectService.findAll());

        model.addAttribute("action", String.format("/tasks/%s", id ));
        model.addAttribute("method", "put");
        model.addAttribute("back", "/tasks");
        model.addAttribute("submit", "Update");
        return "tasks/form";
    }

    private String processUpdateLogic(Task task, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.task", result);
            redirectAttributes.addFlashAttribute("task", task);

            return String.format("redirect:/tasks/%s/edit", task.getId());
        }

        taskService.update(task);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Task successfully updated!", FlashMessage.Status.SUCCESS));
        return "redirect:/tasks";
    }

    // update (partial)
    @RequestMapping(path = "/tasks/{id}", method = RequestMethod.PATCH)
    public String actionUpdatePart(@Valid Task task, BindingResult result, RedirectAttributes redirectAttributes) {
        return processUpdateLogic(task, result, redirectAttributes);
    }


    // update (total)
    @RequestMapping(path = "/tasks/{id}", method = RequestMethod.PUT)
    public String actionUpdateTotal(@Valid Task task, BindingResult result, RedirectAttributes redirectAttributes) {
        return processUpdateLogic(task, result, redirectAttributes);
    }

    // create
    @RequestMapping(path = "/tasks", method = RequestMethod.POST)
    public String actionCreate(@Valid Task task, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.task", result);

            redirectAttributes.addFlashAttribute("task", task);
            return "redirect:/tasks/new";
        }

        taskService.create(task);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Task successfully added!", FlashMessage.Status.SUCCESS));

        return "redirect:/tasks";
    }

    // show
    @RequestMapping(path = "/tasks/{id}", method = RequestMethod.GET)
    public String actionShow(@PathVariable Long id, Model model) {
        Task task = taskService.findOne(id);
        model.addAttribute("task", task);
        return "tasks/show";
    }


    // destroy
    @RequestMapping(path = "/tasks/{id}", method = RequestMethod.DELETE)
    public String actionDestroy(@PathVariable Long id,  RedirectAttributes redirectAttributes) {
        taskService.delete(id);
        return "redirect:/tasks";
    }
}
