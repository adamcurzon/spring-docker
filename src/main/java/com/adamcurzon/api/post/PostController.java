package com.adamcurzon.api.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
public class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository)
    {
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public List<Post> index()
    {
        return this.postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post view(@PathVariable BigInteger id)
    {
        return this.postRepository.findById(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Post post)
    {
        this.postRepository.create(post);
    }
}
