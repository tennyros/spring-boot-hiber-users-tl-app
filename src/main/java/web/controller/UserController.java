package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private static final String REDIRECT = "redirect:/";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String showUsers(Model model) {
        User user = new User("Mike", "Miners", "mike@mail.com", 30);
        userService.addUser(user);
//        System.out.println("Users: " + userService.getAllUsers());
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new_user";
    }

    @PostMapping(value = "/save")
    public String saveUser(@Valid @ModelAttribute User user,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getFieldErrors());
            return "new_user";
        }
        userService.addUser(user);
        return REDIRECT;
    }

    @GetMapping(value = "/edit")
    public String editUserForm(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "update_user";
    }

    @PostMapping(value = "/update")
    public String updateUser(@Valid @ModelAttribute User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getFieldErrors());
            return "update_user";
        }
        userService.updateUser(user);
        return REDIRECT;
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return REDIRECT;
    }
}
