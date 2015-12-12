package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.yandex.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestYandexService implements YandexService {

    public static final String REST_HOST = "https://api-metrika.yandex.ru/";
    public static final String COUNTERS = "counters";

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UserService userService;

    @Override
    public List<Counter> getCounters() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "OAuth " + userService.getUser().getToken());
        httpHeaders.add("Accept", "application/x-yametrika+json");
        HttpEntity<Counter> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(REST_HOST + COUNTERS, HttpMethod.GET, entity, String.class);
        return null;
    }
}
