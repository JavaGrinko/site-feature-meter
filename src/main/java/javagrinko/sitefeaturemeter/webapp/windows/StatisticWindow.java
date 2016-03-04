package javagrinko.sitefeaturemeter.webapp.windows;

import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import javagrinko.sitefeaturemeter.dom.Experiment;
import javagrinko.sitefeaturemeter.dom.yandex.Accounts;
import javagrinko.sitefeaturemeter.dom.yandex.Attendance;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceTotals;
import javagrinko.sitefeaturemeter.services.ExperimentProcessor;
import javagrinko.sitefeaturemeter.services.YandexService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.Math.abs;

@Component
public class StatisticWindow extends Window {

    private final NumberStyleFormatter nsf = new NumberStyleFormatter();

    @Autowired
    private ExperimentProcessor experimentProcessor;

    @Autowired
    private YandexService yandexService;

    @Value("${algorithm.experiment.days}")
    private int experimentDaysCount;

    private Table statisticTable;

    private Experiment experiment;

    public StatisticWindow() {
        super("Анализ статистики");
        setHeight("600px");
        setWidth("900px");
        center();
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();

        initTable();

        content.addComponent(statisticTable);
        setContent(content);
    }

    private void initTable() {
        statisticTable = new Table();
        statisticTable.addContainerProperty("Parameter", String.class, null);
        statisticTable.addContainerProperty("Before", Number.class, null);
        statisticTable.addContainerProperty("After", Number.class, null);
        statisticTable.addContainerProperty("Result", String.class, null);
        statisticTable.addContainerProperty("Real result", String.class, null);
        statisticTable.setSizeFull();
        statisticTable.setSelectable(true);
        statisticTable.setEditable(false);
        statisticTable.setNullSelectionAllowed(false);
        statisticTable.setMultiSelect(false);
        statisticTable.setImmediate(true);
    }


    public void show(UI ui, Experiment experiment) {
        this.experiment = experiment;
        if (getParent() != null) {
            close();
        }
        Accounts accounts = yandexService.getAccounts();
        ui.addWindow(this);
        Long counterId = experiment.getCounterId();
        Date startDate = experiment.getStartDate();

        List<Attendance> attendancesBefore = yandexService.getAttendances(new DateTime(startDate).minusDays(experimentDaysCount).toDate(), startDate, counterId);
        List<Attendance> attendancesAfter = yandexService.getAttendances(startDate, new DateTime(startDate).plusDays(experimentDaysCount).toDate(), counterId);

        AttendanceTotals totalsBefore = attendancesBefore.get(0).getTotals();
        AttendanceTotals totalsAfter = attendancesAfter.get(0).getTotals();

        AttendanceTotals deltaTotals = totalsAfter.minus(totalsBefore);
        List<StatisticRow> rowList = new ArrayList<>();
        rowList.add(new StatisticRow("Visitors", totalsBefore.getVisitors(), totalsAfter.getVisitors()));
        rowList.add(new StatisticRow("Denial", totalsBefore.getDenial(), totalsAfter.getDenial()));
        rowList.add(new StatisticRow("Visits", totalsBefore.getVisits(), totalsAfter.getVisits()));
        rowList.add(new StatisticRow("Depth", totalsBefore.getDepth(), totalsAfter.getDepth()));
        rowList.add(new StatisticRow("Page views", totalsBefore.getPageViews(), totalsAfter.getPageViews()));
        rowList.add(new StatisticRow("Visit time", totalsBefore.getVisitTime(), totalsAfter.getVisitTime()));
        rowList.add(new StatisticRow("New visitors", totalsBefore.getNewVisitors(), totalsAfter.getNewVisitors()));

        Collections.sort(rowList, (a,b) -> abs(a.getDeltaPercentage()) > abs(b.getDeltaPercentage()) ? -1 : 1);
        for (int i = 0; i < rowList.size(); i++) {
            StatisticRow it = rowList.get(i);
            statisticTable.addItem(new Object[]{it.getName(), it.getValueBefore(), it.getValueAfter(), String.valueOf(nsf.print(it.getDelta(), Locale.ENGLISH) + " (" + nsf.print(it.getDeltaPercentage(), Locale.ENGLISH) + "%)"), String.valueOf(nsf.print(it.getDeltaWithNoise(), Locale.ENGLISH) + " (" + nsf.print(it.getDeltaWithNoisePercentage(), Locale.ENGLISH) + "%)")}, i);
        }
        statisticTable.setPageLength(statisticTable.size());
    }
}
