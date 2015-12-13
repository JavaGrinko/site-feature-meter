package javagrinko.sitefeaturemeter.dom.yandex;

import java.util.List;

public class Counters {
    private int rows;
    private List<Counter> counters;

    public List<Counter> getCounters() {
        return counters;
    }

    public void setCounters(List<Counter> counters) {
        this.counters = counters;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
