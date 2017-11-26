package com.devsh.itracker.controller;

import com.devsh.itracker.model.User;
import com.devsh.itracker.service.UserService;
import com.devsh.itracker.service.exception.UserNameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class LoginsController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "/loginForm", method = RequestMethod.GET)
    String loginForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }

        model.addAttribute("action", "/login");
        return "loginForm";
    }

//    @RequestMapping(path = "/projects", method = RequestMethod.POST)
//    public String actionCreate(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
//        if (result.hasErrors()) {
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
//
//            redirectAttributes.addFlashAttribute("project", project);
//            return "redirect:/projects/new";
//        }
//
//        projectService.create(project);
//        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project successfully added!", FlashMessage.Status.SUCCESS));
//
//        return "redirect:/projects";
//    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    String login(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);

            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/loginForm";
        }

        User loginUser = userService.findByUsername(user.getUsername());
        if(loginUser == null) {
            throw new UserNameNotFoundException();
        }


        return "redirect:/tasks";
    }
}
