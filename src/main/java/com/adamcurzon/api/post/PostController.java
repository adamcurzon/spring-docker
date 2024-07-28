package com.adamcurzon.api.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public List<Post> getTest(){
        return this.postRepository.findAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Post post){
        this.postRepository.create(post);
    }

    @GetMapping("/womp")
    public Map<String, String> womp(){
        return Map.of("womp", "womp");
    }
}
