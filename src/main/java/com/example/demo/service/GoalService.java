package com.example.demo.service;

import com.example.demo.model.Goal;
import com.example.demo.repository.GoalRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public Map<String, Object> calculateProgress(Long id) {
        Goal goal = goalRepository.findById(id).orElseThrow();
        double current = transactionRepository.findAll().stream()
                .filter(t -> "INCOME".equals(t.getType()))
                .mapToDouble(t -> t.getAmount()).sum();

        double percent = (goal.getTargetAmount() > 0) ? (current / goal.getTargetAmount()) * 100 : 0;
        return Map.of("goalName", goal.getName(), "percent", Math.min(percent, 100));
    }
}