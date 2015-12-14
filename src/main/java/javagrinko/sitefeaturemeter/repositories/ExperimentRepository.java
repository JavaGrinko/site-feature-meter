package javagrinko.sitefeaturemeter.repositories;

import javagrinko.sitefeaturemeter.dom.Experiment;
import org.springframework.data.repository.CrudRepository;

public interface ExperimentRepository extends CrudRepository<Experiment, Integer> {
}
