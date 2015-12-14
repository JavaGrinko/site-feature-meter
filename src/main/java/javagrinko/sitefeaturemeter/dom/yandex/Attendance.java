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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getCounterId() {
        return counterId;
    }

    public void setCounterId(Long counterId) {
        this.counterId = counterId;
    }

    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }

    public List<AttendanceData> getData() {
        return data;
    }

    public void setData(List<AttendanceData> data) {
        this.data = data;
    }

    public AttendanceTotals getTotals() {
        return totals;
    }

    public void setTotals(AttendanceTotals totals) {
        this.totals = totals;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
