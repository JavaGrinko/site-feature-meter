package javagrinko.sitefeaturemeter.main;

import javagrinko.sitefeaturemeter.converters.YandexOAuthResponseConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashSet;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "javagrinko.sitefeaturemeter")
@ComponentScan("javagrinko.sitefeaturemeter")
public class SiteFeatureMeterApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SiteFeatureMeterApplication.class);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(SiteFeatureMeterApplication.class).run(args);
    }

    @Bean
    LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        HashSet<Converter> converters = new HashSet<>();
        converters.add(new YandexOAuthResponseConverter());
        conversionServiceFactoryBean.setConverters(converters);
        return conversionServiceFactoryBean;
    }
}