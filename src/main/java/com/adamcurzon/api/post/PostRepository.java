package com.adamcurzon.api.post;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class PostRepository {
    private final JdbcClient jdbcClient;

    public PostRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    public List<Post> findAll(){
        return this.jdbcClient.sql("select * from posts").query(Post.class).list();
    }

    public void create(Post post){
        int updated = this.jdbcClient.sql("INSERT INTO posts(message,created_at) VALUES (?, NOW());")
                .params(List.of(post.getMessage()))
                .update();

        Assert.state(updated == 1, "Failed to create post");
    }
}
