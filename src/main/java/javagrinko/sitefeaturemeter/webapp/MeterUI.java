package javagrinko.sitefeaturemeter.webapp;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class MeterUI  extends UI {
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        Label label = new Label("Hello!");
        verticalLayout.addComponent(label);
    }
}
