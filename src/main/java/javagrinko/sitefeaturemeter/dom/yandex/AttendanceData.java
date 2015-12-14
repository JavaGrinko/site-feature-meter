package javagrinko.sitefeaturemeter.dom.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javagrinko.sitefeaturemeter.converters.YandexJsonDateDeserializer;

import java.util.Date;

public class AttendanceData {

    Long id;

    Double denial;

    @JsonDeserialize(using = YandexJsonDateDeserializer.class)
    Date date;

    Long visits;

    Long visitors;

    Double depth;

    @JsonProperty("page_views")
    Long pageViews;

    @JsonProperty("visit_time")
    Long visitTime;

    @JsonProperty("new_visitors")
    Long newVisitors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDenial() {
        return denial;
    }

    public void setDenial(Double denial) {
        this.denial = denial;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getVisits() {
        return visits;
    }

    public void setVisits(Long visits) {
        this.visits = visits;
    }

    public Long getVisitors() {
        return visitors;
    }

    public void setVisitors(Long visitors) {
        this.visitors = visitors;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Long getPageViews() {
        return pageViews;
    }

    public void setPageViews(Long pageViews) {
        this.pageViews = pageViews;
    }

    public Long getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Long visitTime) {
        this.visitTime = visitTime;
    }

    public Long getNewVisitors() {
        return newVisitors;
    }

    public void setNewVisitors(Long newVisitors) {
        this.newVisitors = newVisitors;
    }
}
