package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.yandex.Accounts;
import javagrinko.sitefeaturemeter.dom.yandex.Attendance;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RestYandexService implements YandexService {

    Logger logger = LoggerFactory.getLogger(RestYandexService.class);

    public static final String HOST_URL = "https://api-metrika.yandex.ru/";
    public static final String NEW_HOST_URL = "https://beta.api-metrika.yandex.ru/";

    public static final String COUNTERS = "counters.json";
    public static final String ATTENDANCE = "stat/traffic/summary.json";
    public static final String ACCOUNTS = "management/v1/accounts";

    private RestTemplate restTemplate = new RestTemplate();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private UserService userService;

    @Override
    public Counters getCounters() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "OAuth " + userService.getUser().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Counters> exchange = restTemplate.exchange(HOST_URL + COUNTERS, HttpMethod.GET, entity, Counters.class);
        return exchange.getBody();
    }

    @Override
    public List<Attendance> getAttendances(Date startDate, Date endDate, Long counterID) {
        List<Attendance> attendances = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "OAuth " + userService.getUser().getToken());
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(HOST_URL)
                .path(ATTENDANCE)
                .queryParam("id", counterID);
        if (startDate != null) {
            uri.queryParam("date1", dateFormat.format(startDate));
        }
        if (endDate != null) {
            uri.queryParam("date2", dateFormat.format(endDate));
        }

        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Attendance> exchange = restTemplate.exchange(uri.build().toUri(), HttpMethod.GET, entity, Attendance.class);
        Attendance body = exchange.getBody();
        attendances.add(body);
        while (body.getLinks() != null){
            exchange = restTemplate.exchange(body.getLinks().getNext(), HttpMethod.GET, entity, Attendance.class);
            body = exchange.getBody();
            attendances.add(body);
        }
        return attendances;
    }

    @Override
    public Accounts getAccounts() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "OAuth " + userService.getUser().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Accounts> exchange = restTemplate.exchange(NEW_HOST_URL + ACCOUNTS, HttpMethod.GET, entity, Accounts.class);
        return exchange.getBody();
    }
}