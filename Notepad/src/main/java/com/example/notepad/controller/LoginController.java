package com.example.notepad.controller;

import com.example.notepad.model.User;
import com.example.notepad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String showPage() {
        return "loginPage";
    }

    @PostMapping("/login/error")
    public String loginError(@RequestParam String username, Model model) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && !user.get().isNonBlocked()) {
            model.addAttribute("message", "Пользователь заблокирован!");
        }
        else {
            model.addAttribute("message", "Неправильный логин или пароль!");
        }

        return "loginPage";
    }
}
