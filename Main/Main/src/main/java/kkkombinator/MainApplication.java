package kkkombinator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner runner() {
//		return args -> {
//			System.out.println("Приложение запущено! Откройте http://localhost:8080");
//			Thread.sleep(Long.MAX_VALUE); // Блокируем поток
//		};
//	}
//
//	@GetMapping("/")
//	public String home() {
//		return "OK";
//	}

}
