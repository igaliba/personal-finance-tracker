package com.example.demo.controller;

import com.example.demo.model.Goal;
import com.example.demo.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin("*")
public class GoalController {
    @Autowired
    private GoalRepository goalRepository;

    @GetMapping
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    @PostMapping
    public Goal createGoal(@RequestBody Goal goal) {
        return goalRepository.save(goal);
    }
}