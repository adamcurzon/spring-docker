package com.adamcurzon.api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;

@RestController
public class TestController {
    @GetMapping("/")
    public HashMap<String, String> getTest(){
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Hello World!");
        map.put("author", "adamcurzon");
        map.put("system_time", new Timestamp(System.currentTimeMillis()).toString());
        return map;

    }
}
