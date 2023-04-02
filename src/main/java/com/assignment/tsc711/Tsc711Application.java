package com.assignment.tsc711;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @SpringBootApplication
// @RestController
// public class Tsc711Application {

// 	public static void main(String[] args) {
// 		SpringApplication.run(Tsc711Application.class, args);
// 	}

// 	@GetMapping("/hello")
//     public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
//       return String.format("Hello %s!", name);
//     }

// 	@Bean
//     public Function<String, String> reverseString() {
//         return value -> new StringBuilder(value).reverse().toString();
//     }
// }

@SpringBootApplication
public class Tsc711Application {

    public static void main(String[] args) {
        SpringApplication.run(Tsc711Application.class, args);
    }
}
