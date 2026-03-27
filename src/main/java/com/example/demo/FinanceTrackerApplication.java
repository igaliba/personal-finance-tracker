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
	CommandLineRunner start(CategoryRepository categoryRepository) {
		return args -> {
			// ON VÉRIFIE SI LA LISTE EST VIDE AVANT D'AJOUTER
			if (categoryRepository.count() == 0) {
				Category cat1 = new Category();
				cat1.setName("Alimentation");
				categoryRepository.save(cat1);

				Category cat2 = new Category();
				cat2.setName("Loyer");
				categoryRepository.save(cat2);

				Category cat3 = new Category();
				cat3.setName("Loisirs");
				categoryRepository.save(cat3);
			}else {
				System.out.println("Les catégories existent déjà, on n'ajoute rien.");
			}
		};
	}
}