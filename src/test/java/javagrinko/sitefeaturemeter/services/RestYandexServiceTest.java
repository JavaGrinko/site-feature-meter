package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.yandex.Accounts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RestYandexServiceTest {

    @Autowired
    YandexService yandexService;

    @Test
    public void testGetAccounts() throws Exception {
        Accounts accounts = yandexService.getAccounts();
    }
}