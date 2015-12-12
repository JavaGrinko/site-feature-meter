package javagrinko.sitefeaturemeter.webapp.windows;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class LoginWindow extends Window {

    public static final String CAPTION = "Авторизация";

    public static final String HOST = "https://oauth.yandex.ru/authorize?response_type=token&client_id=";

    @Value("${oauth.id}")
    private String oauthId;

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
        submitButton = new Button("Подключиться к <b><font color=\"#ff0000\">Яндекс</font>Метрика</b>");
        submitButton.setHtmlContentAllowed(true);
        submitButton.setWidth("100%");
        submitButton.addClickListener(event -> Page.getCurrent().open(HOST+oauthId, null));
    }

    public void show(UI ui) {
        if (getParent() != null){
            close();
        }
        ui.addWindow(this);
    }
}
