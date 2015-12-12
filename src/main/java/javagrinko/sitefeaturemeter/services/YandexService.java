package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.yandex.Counter;

import java.util.List;

public interface YandexService {
    List<Counter> getCounters();
}
