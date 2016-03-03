package javagrinko.sitefeaturemeter.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;
import javax.annotation.Resources;

@Configuration
@ComponentScan("javagrinko.sitefeaturemeter")
@PropertySource("application.properties")
public class TestConfig {
}
