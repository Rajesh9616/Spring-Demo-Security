package com.concretepage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@SpringBootApplication
public class MyApplication {  
	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
		/*
		 * BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		 * System.out.println(encoder.encode("m123"));
		 */
        }
} 