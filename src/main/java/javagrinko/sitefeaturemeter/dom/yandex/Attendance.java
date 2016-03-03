package javagrinko.sitefeaturemeter.dom.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javagrinko.sitefeaturemeter.converters.YandexJsonDateDeserializer;

import java.util.Date;
import java.util.List;

public class Attendance {

    @JsonProperty("date1")
    @JsonDeserialize(using = YandexJsonDateDeserializer.class)
    Date startDate;

    @JsonProperty("date2")
    @JsonDeserialize(using = YandexJsonDateDeserializer.class)
    Date endDate;

    @JsonProperty("id")
    Long counterId;

    @JsonProperty("rows")
    Integer dataCount;

    List<AttendanceData> data;

    AttendanceTotals totals;

    Links links;

    public List<AttendanceData> getData() {
        return data;
    }

    public void setData(List<AttendanceData> data) {
        this.data = data;
    }

    public Links getLinks() {
        return links;
    }

    public AttendanceTotals getTotals() {
        return totals;
    }
}
