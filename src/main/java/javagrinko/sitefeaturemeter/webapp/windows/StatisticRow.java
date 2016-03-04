package javagrinko.sitefeaturemeter.webapp.windows;

import org.springframework.format.number.NumberStyleFormatter;

import java.util.Random;

public class StatisticRow {
    private String name;
    private double valueBefore;
    private double valueAfter;
    private Random r = new Random();


    public StatisticRow() {
    }

    public StatisticRow(String name, double valueBefore, double valueAfter) {
        this.name = name;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
    }

    public double getDelta(){
        return valueAfter - valueBefore;
    }

    public double getDeltaPercentage(){
        return getDelta() * 100. / valueBefore;
    }

    public double getDeltaWithNoise(){
        return getDelta() - getDelta() / (r.nextInt(10) + 1);
    }

    public double getDeltaWithNoisePercentage(){
        return getDeltaWithNoise() * 100. / valueBefore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValueBefore() {
        return valueBefore;
    }

    public void setValueBefore(double valueBefore) {
        this.valueBefore = valueBefore;
    }

    public double getValueAfter() {
        return valueAfter;
    }

    public void setValueAfter(double valueAfter) {
        this.valueAfter = valueAfter;
    }
}
