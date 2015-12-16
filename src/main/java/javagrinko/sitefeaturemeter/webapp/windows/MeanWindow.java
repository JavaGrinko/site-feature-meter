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
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceDataMean;
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
        DataSeries dataSeries = new DataSeries("Математическое ожидание");
        Date experimentStartDate = experiment.getStartDate();
        Date startDate = new DateTime(experimentStartDate).minusDays(90).toDate();
        Date endDate = new DateTime(experimentStartDate).plusDays(90).toDate();
        List<Attendance> attendances = yandexService.getAttendances(startDate, endDate, experiment.getCounterId());
        List<AttendanceData> data = new ArrayList<>();
        for (Attendance attendance : attendances) {
            data.addAll(attendance.getData());
        }
        List<AttendanceDataMean> attendanceDataMeanList = experimentProcessor.getAttendanceDataMeanList(data);
        for (AttendanceDataMean mean : attendanceDataMeanList) {
            dataSeries.add(new DataSeriesItem(mean.getDate(), mean.getVisitors()));
        }
        Configuration configuration = new Configuration();
        configuration.setTitle("Математическое ожидание");
        configuration.setSeries(dataSeries);
        meanChart.setConfiguration(configuration);
    }
}
