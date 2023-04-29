package ru.naumen.testerudite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@SpringBootApplication
public class TestEruditeApplication {

	@Autowired
	private FileService fileService;

	public static void main(String[] args) {
		SpringApplication.run(TestEruditeApplication.class, args);
	}
	@EventListener(ApplicationReadyEvent.class)
	@org.springframework.core.annotation.Order(1)
	public void getFile() throws IOException {
		fileService.getNamesFromFile();
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("PUT", "DELETE",
						"GET", "POST", "PATCH");
			}
		};
	}

}
