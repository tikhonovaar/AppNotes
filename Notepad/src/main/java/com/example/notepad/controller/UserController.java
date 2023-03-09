package com.example.notepad.controller;

import com.example.notepad.model.User;
import com.example.notepad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/userInfo")
    public String showUserInfo(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName()).get();
        model.addAttribute("user", user);
        return "userInfoPage";
    }

    @PostMapping("/userInfo")
    public String updateUserInfo(Principal principal, @RequestParam String email, @RequestParam String username) {
        User user = userRepository.findByUsername(principal.getName()).get();
        user.setEmail(email);
        user.setUsername(username);
        userRepository.save(user);
        return "redirect:/login";
    }
}
