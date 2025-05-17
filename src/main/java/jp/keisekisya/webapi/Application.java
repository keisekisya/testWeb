package jp.keisekisya.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// System.out.println(new BCryptPasswordEncoder().encode("mypassword"));
		SpringApplication.run(Application.class, args);
	}
}
