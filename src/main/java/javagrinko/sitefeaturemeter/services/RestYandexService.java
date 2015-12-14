package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.yandex.Counters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class RestYandexService implements YandexService {
    Logger logger = LoggerFactory.getLogger(RestYandexService.class);

    public static final String AUTH_URL = "https://api-metrika.yandex.ru/";
    public static final String DATA_URL = "https://api-metrika.yandex.ru/stat/v1/data";

    public static final String COUNTERS = "counters";

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private HttpSession session;

    @Override
    public Counters getCounters() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "OAuth " + session.getAttribute("token"));
        httpHeaders.add("Accept", "application/x-yametrika+json");
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Counters> exchange = restTemplate.exchange(AUTH_URL + COUNTERS, HttpMethod.GET, entity, Counters.class);
        return exchange.getBody();
    }

    @Override
    public List<Integer> getMetricUsers(Date startDate, Date endDate, Long counterID) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "OAuth " + session.getAttribute("token"));
        httpHeaders.add("Accept", "application/x-yametrika+json");
        httpHeaders.add("ids", String.valueOf(counterID));
        httpHeaders.add("metrics", "ym:pv:users");
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(DATA_URL, HttpMethod.GET, entity, String.class);
        return null;
    }
}