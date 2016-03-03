package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.Experiment;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceData;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceStatistic;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

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
    public List<AttendanceStatistic> getAttendanceStatisticEvolutionList(List<AttendanceData> attendances) {
        AttendanceData sumAttendance = new AttendanceData();
        initSumAttendance(sumAttendance);
        List<AttendanceStatistic> attendanceStatistics = new ArrayList<>();
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

            AttendanceStatistic statistic = new AttendanceStatistic();

            statistic.setDate(attendance.getDate());

            double n = i + 1.;

            double meanDenial = sumDenial / n;
            statistic.setMeanDenial(meanDenial);
            double meanDepth = sumDepth / n;
            statistic.setMeanDepth(meanDepth);
            double meanNewVisitors = sumNewVisitors / n;
            statistic.setMeanNewVisitors(meanNewVisitors);
            double meanPageViews = sumPageViews / n;
            statistic.setMeanPageViews(meanPageViews);
            double meanVisits = sumVisits / n;
            statistic.setMeanVisits(meanVisits);
            double meanVisitors = sumVisitors / n;
            statistic.setMeanVisitors(meanVisitors);
            double meanVisitTime = sumVisitTime / n;
            statistic.setMeanVisitTime(meanVisitTime);

            AttendanceData sigmaSumAttendance = new AttendanceData();
            initSumAttendance(sigmaSumAttendance);
            for (int j = 0; j < n; j++) {
                AttendanceData attendanceData = attendances.get(i);
                Long visits1 = attendanceData.getVisits();

                sigmaSumAttendance.setVisits((long) pow(visits1 - meanVisits, 2));
            }

            statistic.setSigmaVisits(sqrt(sigmaSumAttendance.getVisits() / n));

            attendanceStatistics.add(statistic);
        }
        return attendanceStatistics;
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
