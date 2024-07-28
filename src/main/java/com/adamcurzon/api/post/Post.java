package com.adamcurzon.api.post;

import java.time.LocalDateTime;

public class Post {
    private Integer id;
    private String message;
    private LocalDateTime created_at;

    public Post(Integer id, String message, LocalDateTime created_at){
        this.id = id;
        this.message = message;
        this.created_at = created_at;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
