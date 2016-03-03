package javagrinko.sitefeaturemeter.dom.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AttendanceTotals {

    Double denial;

    Long visits;

    Long visitors;

    Double depth;

    @JsonProperty("page_views")
    Long pageViews;

    @JsonProperty("visit_time")
    Long visitTime;

    @JsonProperty("new_visitors")
    Long newVisitors;

    public AttendanceTotals minus(AttendanceTotals totals){
        AttendanceTotals minusTotals = new AttendanceTotals();
        minusTotals.setDepth(this.getDepth() - totals.getDepth());
        minusTotals.setPageViews(this.getPageViews() - totals.getPageViews());
        minusTotals.setVisits(this.getVisits() - totals.getVisits());
        minusTotals.setVisitors(this.getVisitors() - totals.getVisitors());
        minusTotals.setDenial(this.getDenial() - totals.getDenial());
        minusTotals.setNewVisitors(this.getNewVisitors() - totals.getNewVisitors());
        minusTotals.setVisitTime(this.getVisitTime() - totals.getVisitTime());
        return minusTotals;
    }

    public Double getDenial() {
        return denial;
    }

    public void setDenial(Double denial) {
        this.denial = denial;
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
