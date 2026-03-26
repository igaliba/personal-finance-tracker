package com.example.demo.controller;

import com.example.demo.model.Goal;
import com.example.demo.model.Transaction;
import com.example.demo.repository.GoalRepository;
import com.example.demo.repository.TransactionRepository; // Import bien présent
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin("*")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    @PostMapping
    public Goal createGoal(@RequestBody Goal goal) {
        return goalRepository.save(goal);
    }

    @GetMapping("/{id}/progress")
    public Map<String, Object> getGoalProgress(@PathVariable Long id) {
        Goal goal = goalRepository.findById(id).orElseThrow();

        List<Transaction> transactions = transactionRepository.findAll();

        double currentSavings = transactions.stream()
                .filter(t -> "INCOME".equals(t.getType()))
                .mapToDouble(Transaction::getAmount).sum();

        double progress = (goal.getTargetAmount() > 0) ? (currentSavings / goal.getTargetAmount()) * 100 : 0;

        return Map.of(
                "goalName", goal.getName(),
                "target", goal.getTargetAmount(),
                "current", currentSavings,
                "percent", Math.min(progress, 100)
        );
    }
}