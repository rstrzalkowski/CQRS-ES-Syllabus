package pl.lodz.p.it.rstrzalkowski.syllabus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class CqrsEsSyllabusApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqrsEsSyllabusApplication.class, args);
    }
}
