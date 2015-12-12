package javagrinko.sitefeaturemeter.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("javagrinko.sitefeaturemeter")
public class SiteFeatureMeterApplication {
    public static void main(String[] args) {
        SpringApplication.run(SiteFeatureMeterApplication.class, args);
    }
}