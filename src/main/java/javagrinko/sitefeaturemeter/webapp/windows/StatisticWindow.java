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
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class StatisticWindow extends Window {

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
        statisticTable.addContainerProperty("Result", Number.class, null);
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

        statisticTable.addItem(new Object[]{"Visitors", totalsBefore.getVisitors(), totalsAfter.getVisitors(), deltaTotals.getVisitors()}, 0);
        statisticTable.addItem(new Object[]{"Denial", totalsBefore.getDenial(), totalsAfter.getDenial(), deltaTotals.getDenial()}, 1);
        statisticTable.addItem(new Object[]{"Visits", totalsBefore.getVisits(), totalsAfter.getVisits(), deltaTotals.getVisits()}, 2);
        statisticTable.addItem(new Object[]{"Depth", totalsBefore.getDepth(), totalsAfter.getDepth(), deltaTotals.getDepth()}, 3);
        statisticTable.addItem(new Object[]{"Page views", totalsBefore.getPageViews(), totalsAfter.getPageViews(), deltaTotals.getPageViews()}, 4);
        statisticTable.addItem(new Object[]{"Visit time", totalsBefore.getVisitTime(), totalsAfter.getVisitTime(), deltaTotals.getVisitTime()}, 5);
        statisticTable.addItem(new Object[]{"New visitors", totalsBefore.getNewVisitors(), totalsAfter.getNewVisitors(), deltaTotals.getNewVisitors()}, 6);
        statisticTable.setPageLength(statisticTable.size());
    }
}
