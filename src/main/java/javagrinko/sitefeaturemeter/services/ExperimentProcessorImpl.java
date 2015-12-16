package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.Experiment;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceData;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceDataMean;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExperimentProcessorImpl implements ExperimentProcessor {

    @Value("${algorithm.experiment.days}")
    private int experimentDaysCount;

    @Override
    public float getProgressValue(Experiment experiment) {
        DateTime startDate = new DateTime(experiment.getStartDate());
        DateTime finishDate = new DateTime(getExperimentFinishDate(experiment));
        DateTime nowDate = new DateTime();

        if (nowDate.isBefore(startDate)) {
            return 0f;
        } else if (nowDate.isAfter(finishDate)) {
            return 1f;
        } else {
            Seconds totalSeconds = Seconds.secondsBetween(startDate, finishDate);
            Seconds passedSeconds = Seconds.secondsBetween(startDate, nowDate);
            float progress = (float) passedSeconds.getSeconds() / (float) totalSeconds.getSeconds();
            return progress;
        }
    }

    @Override
    public boolean isExperimentFinished(Experiment experiment) {
        DateTime nowDate = new DateTime();
        DateTime finishDate = new DateTime(getExperimentFinishDate(experiment));
        return nowDate.isAfter(finishDate);
    }

    @Override
    public Date getExperimentFinishDate(Experiment experiment) {
        DateTime startDate = new DateTime(experiment.getStartDate());
        DateTime endDate = startDate.plusDays(experimentDaysCount);
        return endDate.toDate();
    }

    @Override
    public List<AttendanceDataMean> getAttendanceDataMeanList(List<AttendanceData> attendances) {
        AttendanceData sumAttendance = new AttendanceData();
        initSumAttendance(sumAttendance);
        List<AttendanceDataMean> attendanceDataMeans = new ArrayList<>();
        for (int i = 0; i < attendances.size(); i++) {
            AttendanceData attendance = attendances.get(i);
            Double denial = attendance.getDenial();
            Double depth = attendance.getDepth();
            Long newVisitors = attendance.getNewVisitors();
            Long pageViews = attendance.getPageViews();
            Long visits = attendance.getVisits();
            Long visitors = attendance.getVisitors();
            Long visitTime = attendance.getVisitTime();

            Double sumDenial = sumAttendance.getDenial() + denial;
            sumAttendance.setDenial(sumDenial);
            Double sumDepth = sumAttendance.getDepth() + depth;
            sumAttendance.setDepth(sumDepth);
            Long sumNewVisitors = sumAttendance.getNewVisitors() + newVisitors;
            sumAttendance.setNewVisitors(sumNewVisitors);
            Long sumPageViews = sumAttendance.getPageViews() + pageViews;
            sumAttendance.setPageViews(sumPageViews);
            Long sumVisits = sumAttendance.getVisits() + visits;
            sumAttendance.setVisits(sumVisits);
            Long sumVisitors = sumAttendance.getVisitors() + visitors;
            sumAttendance.setVisitors(sumVisitors);
            Long sumVisitTime = sumAttendance.getVisitTime() + visitTime;
            sumAttendance.setVisitTime(sumVisitTime);

            AttendanceDataMean mean = new AttendanceDataMean();
            mean.setDate(attendance.getDate());
            mean.setDenial(sumDenial / (i + 1));
            mean.setDepth(sumDepth / (i + 1));
            mean.setNewVisitors((sumNewVisitors / (i + 1.)));
            mean.setPageViews(((sumPageViews / (i + 1.))));
            mean.setVisits((sumVisits / (i + 1.)));
            mean.setVisitors((sumVisitors / (i + 1.)));
            mean.setVisitTime((sumVisitTime / (i + 1.)));
            attendanceDataMeans.add(mean);
        }
        return attendanceDataMeans;
    }

    private void initSumAttendance(AttendanceData sumAttendance) {
        sumAttendance.setDenial(0D);
        sumAttendance.setDepth(0D);
        sumAttendance.setNewVisitors(0L);
        sumAttendance.setPageViews(0L);
        sumAttendance.setVisits(0L);
        sumAttendance.setVisitors(0L);
        sumAttendance.setVisitTime(0L);
    }
}
