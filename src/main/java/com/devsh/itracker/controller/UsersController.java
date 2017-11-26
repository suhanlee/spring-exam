package com.devsh.itracker.controller;

import com.devsh.itracker.model.User;
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
public class UsersController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String index(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/index";
    }

    // new
    @RequestMapping(path = "/users/new", method = RequestMethod.GET)
    public String actionNew(Model model) {
        if(!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        model.addAttribute("action", "/users");
        model.addAttribute("method", "post");
        model.addAttribute("back", "/users");
        model.addAttribute("submit", "Add");

        return "users/form";
    }

    // edit
    @RequestMapping(path = "/users/{id}/edit", method = RequestMethod.GET)
    public String actionEdit(@PathVariable Long id, Model model) {
        if(!model.containsAttribute("user")) {
            model.addAttribute("user", userService.findOne(id));
        }
        model.addAttribute("action", String.format("/users/%s", id ));
        model.addAttribute("method", "put");
        model.addAttribute("back", "/users");
        model.addAttribute("submit", "Update");
        return "users/form";
    }

    private String processUpdateLogic(User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);

            return String.format("redirect:/users/%s/edit", user.getId());
        }

        userService.update(user);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("User successfully updated!", FlashMessage.Status.SUCCESS));
        return "redirect:/users";
    }

    // update (partial)
    @RequestMapping(path = "/users/{id}", method = RequestMethod.PATCH)
    public String actionUpdatePart(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        return processUpdateLogic(user, result, redirectAttributes);
    }


    // update (total)
    @RequestMapping(path = "/users/{id}", method = RequestMethod.PUT)
    public String actionUpdateTotal(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        return processUpdateLogic(user, result, redirectAttributes);
    }

    // create
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public String actionCreate(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);

            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/users/new";
        }

        userService.create(user);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("User successfully added!", FlashMessage.Status.SUCCESS));

        return "redirect:/users";
    }

    // show
    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public String actionShow(@PathVariable Long id, Model model) {
        User user = userService.findOne(id);
        model.addAttribute("user", user);
        return "users/show";
    }


    // destroy
    @RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    public String actionDestroy(@PathVariable Long id,  RedirectAttributes redirectAttributes) {
        userService.delete(id);
        return "redirect:/users";
    }


}