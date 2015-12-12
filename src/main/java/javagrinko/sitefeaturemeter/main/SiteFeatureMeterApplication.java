package javagrinko.sitefeaturemeter.main;

import com.mongodb.Mongo;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import javagrinko.sitefeaturemeter.converters.YandexOAuthResponseConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.io.IOException;
import java.util.HashSet;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "javagrinko.sitefeaturemeter")
@ComponentScan("javagrinko.sitefeaturemeter")
public class SiteFeatureMeterApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SiteFeatureMeterApplication.class).banner((environment, sourceClass, out) -> out.print(
                "   ___                  _____      _       _         \n" +
                        "  |_  |                |  __ \\    (_)     | |        \n" +
                        "    | | __ ___   ____ _| |  \\/_ __ _ _ __ | | _____  \n" +
                        "    | |/ _` \\ \\ / / _` | | __| '__| | '_ \\| |/ / _ \\ \n" +
                        "/\\__/ / (_| |\\ V / (_| | |_\\ \\ |  | | | | |   < (_) |\n" +
                        "\\____/ \\__,_| \\_/ \\__,_|\\____/_|  |_|_| |_|_|\\_\\___/ \n" +
                        " JavaGrinko@gmail.com ------- github.com/JavaGrinko  \n" +
                        "                                                     \n")).run(args);
    }

    @Bean(destroyMethod = "close")
    public Mongo mongo() throws IOException {
        return new EmbeddedMongoBuilder()
                .version("2.4.5")
                .bindIp("127.0.0.1")
                .port(12345)
                .build();
    }

    @Bean
    LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    ConversionServiceFactoryBean conversionService(){
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        HashSet<Converter> converters = new HashSet<>();
        converters.add(new YandexOAuthResponseConverter());
        conversionServiceFactoryBean.setConverters(converters);
        return conversionServiceFactoryBean;
    }
}