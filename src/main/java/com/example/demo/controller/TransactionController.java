package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin("*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @GetMapping
    public List<Transaction> getAll() {
        return transactionService.getAll();
    }


    @GetMapping("/dashboard")
    public Map<String, Double> getDashboard() {
        return transactionService.getAmountsByCategory();
    }

    // DASHBOARD : Total Revenus, Dépenses et Solde
    @GetMapping("/dashboard/summary")
    public Map<String, Object> getSummary() {
        return transactionService.getTransactionSummary();
    }


    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        return transactionService.save(transaction);
    }


    @PutMapping("/{id}")
    public Transaction update(@PathVariable Long id, @RequestBody Transaction transaction) {
        transaction.setId(id);
        return transactionService.save(transaction);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        transactionService.deleteById(id);
    }
}