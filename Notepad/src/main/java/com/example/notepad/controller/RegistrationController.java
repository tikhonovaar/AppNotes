package com.example.notepad.controller;

import com.example.notepad.model.Role;
import com.example.notepad.model.User;
import com.example.notepad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.EnumSet;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/registration")
    public String showPage() {
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("email") String email,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("confirmPassword") String confirmPassword,
                          Model model) {

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует!");
            return "registrationPage";
        }

        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("message", "Пользователь с таким логином уже существует!");
            return "registrationPage";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("message", "Пароли не совпадают!");
            return "registrationPage";
        }

        User user = new User(email, username, encoder.encode(password));
        user.setRoles(EnumSet.of(Role.ROLE_USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
