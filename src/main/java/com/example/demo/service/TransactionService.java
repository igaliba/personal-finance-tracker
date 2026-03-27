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

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }
    public Transaction save(Transaction t) {
        return transactionRepository.save(t);
    }

    public List<Transaction> getAllByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

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

        double totalIncome = transactions.stream()
                .filter(t -> t.getType().equals("INCOME"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpenses = transactions.stream()
                .filter(t -> t.getType().equals("EXPENSE"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        Map<String, Object> summary = new HashMap<>();

        summary.put("income", totalIncome);
        summary.put("expense", totalExpenses);
        summary.put("balance", totalIncome - totalExpenses);

        return summary;
    }
}