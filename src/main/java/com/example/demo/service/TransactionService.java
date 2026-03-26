package com.example.demo.service;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Transaction save(Transaction t) {
        return transactionRepository.save(t);
    }

    // Toute la logique de calcul est maintenant ici !
    public Map<String, Double> getAmountsByCategory() {
        return transactionRepository.findAll().stream()
                .filter(t -> "EXPENSE".equals(t.getType()) && t.getCategory() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    public Map<String, Object> getTransactionSummary() {
        List<Transaction> transactions = transactionRepository.findAll();
        double income = transactions.stream().filter(t -> "INCOME".equals(t.getType())).mapToDouble(Transaction::getAmount).sum();
        double expense = transactions.stream().filter(t -> "EXPENSE".equals(t.getType())).mapToDouble(Transaction::getAmount).sum();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", income);
        summary.put("totalExpenses", expense);
        summary.put("balance", income - expense);
        return summary;
    }
}