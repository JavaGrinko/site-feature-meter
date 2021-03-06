package javagrinko.sitefeaturemeter.webapp;

import com.vaadin.annotations.Theme;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import javagrinko.sitefeaturemeter.dom.Experiment;
import javagrinko.sitefeaturemeter.dom.User;
import javagrinko.sitefeaturemeter.dom.yandex.OAuthResponse;
import javagrinko.sitefeaturemeter.main.SiteFeatureMeterProperties;
import javagrinko.sitefeaturemeter.services.ExperimentProcessor;
import javagrinko.sitefeaturemeter.services.ExperimentService;
import javagrinko.sitefeaturemeter.services.UserService;
import javagrinko.sitefeaturemeter.webapp.windows.LoginWindow;
import javagrinko.sitefeaturemeter.webapp.windows.NewExperimentWindow;
import javagrinko.sitefeaturemeter.webapp.windows.StatisticWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import java.util.Date;
import java.util.List;

@SpringUI
@Theme("valo")
//@Widgetset("webapp.Widgetset")
public class MeterUI extends UI {

    @Autowired
    private SiteFeatureMeterProperties siteProperties;

    @Autowired
    private LoginWindow loginWindow;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private NewExperimentWindow newExperimentWindow;

    @Autowired
    private StatisticWindow statisticWindow;

    @Autowired
    private ExperimentProcessor experimentProcessor;

    private Table experimentsTable;
    private Button addExperimentButton;
    private Label versionLabel;

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout content = new VerticalLayout();
        setContent(content);
        content.setMargin(true);
        initLogin();
        initExperimentersTable();
        initAddExperimentButton();
        initVersionLabel();

        content.addComponent(addExperimentButton);
        content.addComponent(experimentsTable);
        content.addComponent(versionLabel);
        content.setComponentAlignment(versionLabel, Alignment.BOTTOM_RIGHT);
    }

    private void initVersionLabel() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Version ")
                .append(siteProperties.getVersion())
                .append(".").append(siteProperties.getSubVersion())
                .append(" build ").append(siteProperties.getBuildVersion());
        versionLabel = new Label(stringBuffer.toString());
        versionLabel.setWidth("200px");
        versionLabel.setHeight("100%");
    }

    private void initAddExperimentButton() {
        addExperimentButton = new Button("+ Добавить эксперимент");
        addExperimentButton.addClickListener(e -> newExperimentWindow.show(this));
    }

    private void initExperimentersTable() {
        experimentsTable = new Table("Эксперименты");
        experimentsTable.setSizeFull();
        experimentsTable.setSelectable(true);
        experimentsTable.setEditable(false);
        experimentsTable.setImmediate(false);
        experimentsTable.addContainerProperty("Описание", String.class, null);
        experimentsTable.addContainerProperty("Дата начала", Date.class, null);
        experimentsTable.addContainerProperty("Осталось дней до завершения", ProgressBar.class, null);
        experimentsTable.addContainerProperty("Результат", Image.class, null);
        experimentsTable.setColumnAlignment("Результат", Table.Align.CENTER);
        List<Experiment> experiments = experimentService.getExperiments();
        for (int i = 0; i < experiments.size(); i++) {
            Experiment experiment = experiments.get(i);
            Image badImage = new Image();
            badImage.setSource(new ThemeResource("images/bad.png"));
            Image questionImage = new Image();
            questionImage.setSource(new ThemeResource("images/question.png"));
            ProgressBar progressBar = new ProgressBar(experimentProcessor.getProgressValue(experiment));
            progressBar.setSizeFull();
            experimentsTable.addItem(new Object[]{experiment.getDescription(),
                                                  experiment.getStartDate(),
                                                  progressBar,
                                                  experimentProcessor.isExperimentFinished(experiment) ? badImage : questionImage}, i+1);
            experimentsTable.addItemClickListener(e -> {
                if (e.isDoubleClick()){
                    ((Table) e.getSource()).select(e.getItemId());
                    Object value1 = experimentsTable.getValue();
                    Experiment value = experiments.get((Integer)value1-1);
                    statisticWindow.show(this, value);
                }
            });

        }
        experimentsTable.setPageLength(experimentsTable.size());
    }

    private void initLogin() {
        User user = userService.getUser();
        if (user == null) {
            String uriFragment = getPage().getUriFragment();
            OAuthResponse response = conversionService.convert(uriFragment, OAuthResponse.class);
            getPage().setUriFragment(null);
            if (response == null || response.getAccessToken() == null) {
                loginWindow.show(getUI());
            } else {
                User newUser = new User();
                newUser.setToken(response.getAccessToken());
                userService.saveUser(newUser);
                new Notification("Авторизация выполнена",
                                "Ключ: " + response.getAccessToken() + "\n" +
                                "Время жизни: " + response.getExpiresInSeconds() + " сек \n" +
                                "Тип ключа: " + response.getTokenType() +
                                ((response.getState() == null) ? "" : ("\nСообщение: " + response.getState())),
                                Notification.Type.TRAY_NOTIFICATION).show(getPage());
            }
        }
    }
}