package com.example.notepad.controller;

import com.example.notepad.model.Note;
import com.example.notepad.repository.UserRepository;
import com.example.notepad.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class NoteController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteService service;

    @GetMapping("/addNote")
    public String showAddNotePage() {

        return "addNotePage";
    }

    @PostMapping("/addNote")
    public String saveNote(Principal principal, @RequestParam String title, @RequestParam String content,
                           @RequestParam(defaultValue = "false") boolean checkbox) {
        Note note = new Note(title, content, checkbox);
        note.setLastUpdate(new Date());

        if (principal != null) {
            note.setAuthor(userRepository.findByUsername(principal.getName()).get());
            service.saveNote(note);
            return "redirect:/edit/" + note.getId();
        }

        service.saveNote(note);

        return "redirect:/";
    }

    @GetMapping("/userNotes")
    public String showNotes(Principal principal, Model model) {
//        List<Note> notes = service.findAllByAuthor(userRepository.findByUsername(principal.getName()).get());
        Note noteF = service.findByAuthorAndId(userRepository.findByUsername(principal.getName()).get(), 1L);
        if (noteF != null){
            List<Note> notes = service.findAllByAuthor(userRepository.findByUsername(principal.getName()).get());
            model.addAttribute("notes", notes);
        }else{
            Note note = new Note("Добро пожаловать!", "Здесь Вы можете создать свою первую заметку!", false);
            note.setLastUpdate(new Date());
            note.setAuthor(userRepository.findByUsername(principal.getName()).get());
            service.saveNote(note);

            List<Note> notes = service.findAllByAuthor(userRepository.findByUsername(principal.getName()).get());
            model.addAttribute("notes", notes);
        }
        return "showUserNotesPage";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, Principal principal) {
        Note note = service.findById(id);

        if (!checkAuthorship(note, principal)) {
            return "redirect:/403";
        }

        model.addAttribute("note", note);
        return "editNotePage";
    }

    @PostMapping("/edit/{id}")
    public String saveNote(@PathVariable Long id, @RequestParam String title, @RequestParam String content,
                           @RequestParam(defaultValue = "false") boolean checkbox) {
        service.updateNote(id, title, content, checkbox);
        return "redirect:/userNotes";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal) {

        Note note = service.findById(id);
        if (!checkAuthorship(note, principal)) {
            return "redirect:/403";
        }

        service.deleteNote(id);
        return "redirect:/userNotes";
    }

    public boolean checkAuthorship(Note note, Principal principal) {
        if (note.getAuthor() != null) {
            return principal.getName().equals(note.getAuthor().getUsername());
        }
        else {
            return false;
        }
    }
}
