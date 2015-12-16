package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.Experiment;
import javagrinko.sitefeaturemeter.dom.yandex.Attendance;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceData;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceDataMean;

import java.util.Date;
import java.util.List;

public interface ExperimentProcessor {
    float getProgressValue(Experiment experiment);

    boolean isExperimentFinished(Experiment experiment);

    Date getExperimentFinishDate(Experiment experiment);

    List<AttendanceDataMean> getAttendanceDataMeanList(List<AttendanceData> attendances);
}
