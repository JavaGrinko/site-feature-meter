package javagrinko.sitefeaturemeter.main;

import javagrinko.sitefeaturemeter.converters.YandexOAuthResponseConverter;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashSet;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "javagrinko.sitefeaturemeter")
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

    /*@Bean
    DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }*/


    @Bean
    @Autowired
    EntityManagerFactory entityManagerFactory(DataSource dataSource){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("javagrinko.sitefeaturemeter.dom");
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/site-feature-meter/console/*");
        return registration;
    }
}