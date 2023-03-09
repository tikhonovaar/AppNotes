package com.example.notepad.repository;

import com.example.notepad.model.Note;
import com.example.notepad.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Long> {

    List<Note> findAllByAuthor(User author);

    List<Note> findAllByPrivacy(boolean privacy);

    Note findByAuthorAndId(User author, long id);
}
