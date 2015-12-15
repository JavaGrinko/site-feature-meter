package javagrinko.sitefeaturemeter.webapp.windows;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import javagrinko.sitefeaturemeter.dom.Experiment;
import javagrinko.sitefeaturemeter.dom.yandex.Counter;
import javagrinko.sitefeaturemeter.dom.yandex.Counters;
import javagrinko.sitefeaturemeter.services.ExperimentService;
import javagrinko.sitefeaturemeter.services.YandexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;

@Component
@UIScope
public class NewExperimentWindow extends Window {

    public static final String CAPTION = "Добавить нововвдение";

    private TextField descriptionTextField;
    private PopupDateField dateField;
    private ComboBox counterComboBox;
    private Button submitButton;

    @Autowired
    private YandexService yandexService;

    @Autowired
    private ExperimentService experimentService;

    private final VerticalLayout content;

    public NewExperimentWindow() {
        super(CAPTION);
        setHeight("330px");
        setWidth("300px");
        center();
        content = new VerticalLayout();
        content.setMargin(true);
        content.setSizeFull();
        setContent(content);
    }

    @PostConstruct
    private void setUp(){
        initDescriptionTextField();
        initDateField();
        initCounterComboBox();
        initSubmitButton();
        content.addComponent(descriptionTextField);
        content.addComponent(dateField);
        content.addComponent(counterComboBox);
        content.addComponent(submitButton);
    }

    private void initSubmitButton() {
        submitButton = new Button("Добавить");
        submitButton.setWidth("100%");
        submitButton.setSizeFull();
        submitButton.addClickListener(e -> {
            if (validate(content)){
                Experiment experiment = new Experiment();
                experiment.setCounterId(((Counter)counterComboBox.getValue()).getId());
                experiment.setStartDate(dateField.getValue());
                experiment.setDescription(descriptionTextField.getValue());

                experimentService.addExperiment(experiment);
                close();
            }
        });
    }

    private void initCounterComboBox() {
        counterComboBox = new ComboBox("Целевой сайт");
        counterComboBox.setWidth("100%");
        counterComboBox.setTextInputAllowed(false);
        counterComboBox.setRequired(true);
        counterComboBox.setNullSelectionAllowed(false);
        BeanItemContainer<Counter> container = new BeanItemContainer<>(Counter.class);
        Counters counters = yandexService.getCounters();
        container.addAll(counters.getCounters());
        counterComboBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        counterComboBox.setItemCaptionPropertyId("siteName");
        counterComboBox.setContainerDataSource(container);
    }

    private void initDateField() {
        dateField = new PopupDateField("Дата внедрения на сайт");
        dateField.setWidth("100%");
        dateField.setRequired(true);
    }

    private void initDescriptionTextField() {
        descriptionTextField = new TextField("Описание нововведения");
        descriptionTextField.setWidth("100%");
        descriptionTextField.setRequired(true);
    }

    public void show(UI ui){
        if (getParent() != null){
            close();
        }
        ui.addWindow(this);
    }

    protected boolean validate(ComponentContainer layout) {
        boolean result = true;
        Iterator<com.vaadin.ui.Component> i = layout.iterator();
        while (i.hasNext()) {
            com.vaadin.ui.Component c = i.next();
            if (c instanceof AbstractField) {
                try {
                    ((AbstractField) c).validate();
                    ((AbstractComponent)c).setComponentError(null);
                } catch (Exception e) {
                    ((AbstractComponent)c).setComponentError(new UserError(e.getMessage()));
                    result = false;
                }
            } else if (c instanceof AbstractComponentContainer) {
                if (!validate((AbstractComponentContainer) c)) {
                    result = false;
                }
            }
        }
        return result;
    }
}
