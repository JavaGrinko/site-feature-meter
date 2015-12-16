package javagrinko.sitefeaturemeter.webapp.windows;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import javagrinko.sitefeaturemeter.dom.Experiment;
import javagrinko.sitefeaturemeter.dom.yandex.Attendance;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceData;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceStatistic;
import javagrinko.sitefeaturemeter.services.ExperimentProcessor;
import javagrinko.sitefeaturemeter.services.YandexService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MeanWindow extends Window{

    Chart meanChart;

    @Autowired
    private ExperimentProcessor experimentProcessor;

    @Autowired
    private YandexService yandexService;
    private Experiment experiment;

    public MeanWindow() {
        super("Анализ статистики");
        setHeight("600px");
        setWidth("900px");
        center();
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();

        initMeanChart();

        content.addComponent(meanChart);
        setContent(content);
    }

    private void initMeanChart() {
        meanChart = new Chart();
        meanChart.setSizeFull();
    }

    public void show(UI ui, Experiment experiment){
        this.experiment = experiment;
        if (getParent() != null){
            close();
        }
        ui.addWindow(this);
        DataSeries meanSeries = new DataSeries("Математическое ожидание");
        DataSeries plusSigmaSeries = new DataSeries("+3sigma");
        DataSeries minusSigmaSeries = new DataSeries("-3sigma");
        Date experimentStartDate = experiment.getStartDate();
        Date startDate = new DateTime(experimentStartDate).minusDays(90).toDate();
        Date endDate = new DateTime(experimentStartDate).plusDays(90).toDate();
        List<Attendance> attendances = yandexService.getAttendances(startDate, endDate, experiment.getCounterId());
        List<AttendanceData> data = new ArrayList<>();
        for (Attendance attendance : attendances) {
            data.addAll(attendance.getData());
        }
        List<AttendanceStatistic> attendanceStatisticList = experimentProcessor.getAttendanceStatisticEvolutionList(data);
        for (AttendanceStatistic statistic : attendanceStatisticList) {
            meanSeries.add(new DataSeriesItem(statistic.getDate(), statistic.getMeanVisitors()));
            plusSigmaSeries.add(new DataSeriesItem(statistic.getDate(), statistic.getSigmaVisitors() * 3));
            minusSigmaSeries.add(new DataSeriesItem(statistic.getDate(), - statistic.getSigmaVisitors() * 3));
        }
        Configuration configuration = new Configuration();
        configuration.setTitle("Математическое ожидание");
        configuration.setSeries(meanSeries, plusSigmaSeries, minusSigmaSeries);
        meanChart.setConfiguration(configuration);
    }
}
