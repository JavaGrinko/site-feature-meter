package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.Experiment;

import java.util.List;

public interface ExperimentService {
    int getExperimentsCount();
    List<Experiment> getExperiments();
    void addExperiment(Experiment experiment);
    void removeExperiment(Experiment experiment);
}
