package kz.iitu.clinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClinicApplication.class, args);
        System.out.println(">>> Clinic Server started! Open http://localhost:8080");
    }
}
