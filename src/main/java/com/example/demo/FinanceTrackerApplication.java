package com.example.demo;

import com.example.demo.model.Category;
import com.example.demo.model.Transaction;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class FinanceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceTrackerApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CategoryRepository catRepo, TransactionRepository transRepo) {
		return args -> {
			// Création de catégories de test
			Category food = new Category(); food.setName("Alimentation");
			Category rent = new Category(); rent.setName("Loyer");
			catRepo.save(food);
			catRepo.save(rent);

			Transaction t1 = new Transaction();
			t1.setDescription("Courses Carrefour");
			t1.setAmount(55.50);
			t1.setDate(LocalDate.now());
			t1.setType("EXPENSE");
			t1.setCategory(food);
			transRepo.save(t1);

			System.out.println("Données de test insérées !");
		};
	}
}