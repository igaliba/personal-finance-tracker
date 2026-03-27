package com.example.demo.controller;

import com.example.demo.model.Goal; // Assure-toi d'avoir ce modèle
import com.example.demo.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin("*")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping
    public List<Goal> getAllGoals() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}/progress")
    public Map<String, Object> getProgress(@PathVariable Long id) {
        return goalService.calculateProgress(id);
    }
}