package dev;

import java.time.ZoneId;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ColleguesApiApplication {

	public static void main(String[] args) {
		
		ZoneId.systemDefault();
		
		SpringApplication.run(ColleguesApiApplication.class, args);
	}

}
