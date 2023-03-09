package com.example.notepad.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Lob
    private String content;
    private Date lastUpdate;
    private boolean privacy;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User author;

    public Note() {}

    public Note(String title, String content, boolean privacy) {
        this.title = title;
        this.content = content;
        this.lastUpdate = new Date();
        this.privacy = privacy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public User getAuthor() {
        return author;
    }

    public String getAuthorName() {
        if (author == null) {
            return "none";
        }
        else {
            return author.getUsername();
        }
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }
}
