package com.example.notepad.controller;

import com.example.notepad.model.Note;
import com.example.notepad.repository.UserRepository;
import com.example.notepad.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteService service;

    @GetMapping(value = {"/", "/mainPage"})
    public String showMainPage(Model model) {
        List<Note> notes = service.findAllByPrivacy(false);
        model.addAttribute("notes", notes);
        return "mainPage";
    }
}
