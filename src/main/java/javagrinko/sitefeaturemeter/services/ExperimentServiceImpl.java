package javagrinko.sitefeaturemeter.services;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import javagrinko.sitefeaturemeter.dom.Experiment;
import javagrinko.sitefeaturemeter.repositories.ExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperimentServiceImpl implements ExperimentService {

    @Autowired
    ExperimentRepository experimentRepository;

    @Override
    public int getExperimentsCount() {
        return (int) experimentRepository.count();
    }

    @Override
    public List<Experiment> getExperiments() {
        return Lists.newArrayList(experimentRepository.findAll());
    }

    @Override
    public void addExperiment(Experiment experiment) {
        experimentRepository.save(experiment);
    }

    @Override
    public void removeExperiment(Experiment experiment) {
        experimentRepository.delete(experiment);
    }
}
