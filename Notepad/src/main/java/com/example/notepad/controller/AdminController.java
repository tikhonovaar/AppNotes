package com.example.notepad.controller;

import com.example.notepad.model.Role;
import com.example.notepad.model.User;
import com.example.notepad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.EnumSet;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public String showUserListPage(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "usersListPage";
    }

    @GetMapping("/delete/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/changeRole/{id}")
    public String changeRoleUser(@PathVariable Long id) {
        User user = userRepository.findById(id).get();

        if (user.getRoles().equals(EnumSet.of(Role.ROLE_USER))) {
            user.setRoles(EnumSet.of(Role.ROLE_ADMIN));
        }
        else {
            user.setRoles(EnumSet.of(Role.ROLE_USER));
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/blockUser/{id}")
    public String blockUser(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        user.setNonBlocked(false);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/unlockUser/{id}")
    public String unlockUser(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        user.setNonBlocked(true);
        userRepository.save(user);
        return "redirect:/users";
    }
}
