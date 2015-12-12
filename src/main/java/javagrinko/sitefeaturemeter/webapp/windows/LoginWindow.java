package javagrinko.sitefeaturemeter.webapp.windows;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class LoginWindow extends Window {

    public static final String CAPTION = "Авторизация";

    Button submitButton;

    public LoginWindow() {
        super(CAPTION);
        setHeight("100px");
        setWidth("500px");
        setModal(true);
        setClosable(false);
        setResizable(false);
        center();
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        initSubmitButton();
        content.addComponent(submitButton);

        setContent(content);
    }

    private void initSubmitButton() {
        submitButton = new Button("Подключиться к Яндекс.Метрика");
        submitButton.setWidth("100%");
        submitButton.addClickListener(event -> {
            Page.getCurrent().open("http://ya.ru", null);
        });
    }

    public void show(UI ui) {
        if (getParent() != null){
            close();
        }
        ui.addWindow(this);
    }
}
