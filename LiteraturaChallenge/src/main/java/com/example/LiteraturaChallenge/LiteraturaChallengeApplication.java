package com.example.LiteraturaChallenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaChallengeApplication implements CommandLineRunner {

	private final MenuApp menuApp;

	public LiteraturaChallengeApplication(MenuApp menuApp) {
		this.menuApp = menuApp;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) {
		menuApp.mostrarMenu();
	}
}
