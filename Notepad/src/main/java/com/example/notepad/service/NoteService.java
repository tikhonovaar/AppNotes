package com.example.notepad.service;

import com.example.notepad.model.Note;
import com.example.notepad.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface NoteService {
    Note findById(Long id);
    void saveNote(Note note);
    void updateNote(Long id, String title, String content, boolean privacy);
    void deleteNote(Long id);

    List<Note> findAllByAuthor(User author);

    Note findByAuthorAndId(User author, long id);

    List<Note> findAllByPrivacy(boolean privacy);
}
