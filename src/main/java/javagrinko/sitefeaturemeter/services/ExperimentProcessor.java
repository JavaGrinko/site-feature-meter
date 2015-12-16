package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.Experiment;

import java.util.Date;

public interface ExperimentProcessor {
    float getProgressValue(Experiment experiment);
    boolean isExperimentFinished(Experiment experiment);
    Date getExperimentFinishDate(Experiment experiment);
}
