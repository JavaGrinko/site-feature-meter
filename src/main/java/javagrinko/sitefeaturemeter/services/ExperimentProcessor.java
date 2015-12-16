package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.Experiment;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceData;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceStatistic;

import java.util.Date;
import java.util.List;

public interface ExperimentProcessor {
    float getProgressValue(Experiment experiment);

    boolean isExperimentFinished(Experiment experiment);

    Date getExperimentFinishDate(Experiment experiment);

    List<AttendanceStatistic> getAttendanceStatisticEvolutionList(List<AttendanceData> attendances);
}
