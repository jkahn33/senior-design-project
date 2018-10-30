package senior.design.group10.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages={"senior.design.group10.objects",
    "senior.design.group10.controller",
    "senior.design.group10.dao",
    "senior.design.group10.application",
    "senior.design.group10.service"})
@SpringBootApplication
@EnableJpaRepositories("senior.design.group10.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
