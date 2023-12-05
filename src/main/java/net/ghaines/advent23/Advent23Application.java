package net.ghaines.advent23;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Advent23Application {

	public static void main(String[] args) {
		SpringApplication.run(Advent23Application.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(Day1 puzzle) {
		return args -> {
			puzzle.run();
		};
	}

}
