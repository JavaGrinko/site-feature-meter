package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.yandex.Attendance;
import javagrinko.sitefeaturemeter.dom.yandex.Counters;

import java.util.Date;
import java.util.List;

public interface YandexService {
    Counters getCounters();
    List<Attendance> getAttendances(Date startDate, Date endDate, Long counterID);
}
