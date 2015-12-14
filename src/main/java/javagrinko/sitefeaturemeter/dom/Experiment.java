package javagrinko.sitefeaturemeter.dom;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "experiments")
public class Experiment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NotNull
    @Column
    String description;

    String pathToImage;

    @NotNull
    @Column
    Date startDate;

    @NotNull
    @Column
    Long counterId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
