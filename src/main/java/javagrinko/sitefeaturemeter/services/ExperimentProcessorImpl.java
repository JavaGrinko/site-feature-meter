package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.Experiment;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
