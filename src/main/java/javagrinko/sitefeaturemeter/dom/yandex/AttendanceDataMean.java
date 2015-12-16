package javagrinko.sitefeaturemeter.dom.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javagrinko.sitefeaturemeter.converters.YandexJsonDateDeserializer;

import java.util.Date;

public class AttendanceDataMean {

    Date date;

    Double denial;

    Double visits;

    Double visitors;

    Double depth;

    Double pageViews;

    Double visitTime;

    Double newVisitors;

    public Double getDenial() {
        return denial;
    }

    public void setDenial(Double denial) {
        this.denial = denial;
    }

    public Double getVisits() {
        return visits;
    }

    public void setVisits(Double visits) {
        this.visits = visits;
    }

    public Double getVisitors() {
        return visitors;
    }

    public void setVisitors(Double visitors) {
        this.visitors = visitors;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getPageViews() {
        return pageViews;
    }

    public void setPageViews(Double pageViews) {
        this.pageViews = pageViews;
    }

    public Double getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Double visitTime) {
        this.visitTime = visitTime;
    }

    public Double getNewVisitors() {
        return newVisitors;
    }

    public void setNewVisitors(Double newVisitors) {
        this.newVisitors = newVisitors;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
