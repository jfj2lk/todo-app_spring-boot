package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class TodoController {
    @GetMapping("/todos")
    public Map<String, String> getMethodName() {
        return Map.of("message", "get request!");
    }

    @PostMapping("/todos")
    public String postMethodName(@RequestBody String entity) {
        return entity;
    }
}
