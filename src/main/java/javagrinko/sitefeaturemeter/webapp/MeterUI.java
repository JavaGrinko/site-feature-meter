package javagrinko.sitefeaturemeter.webapp;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javagrinko.sitefeaturemeter.dom.User;
import javagrinko.sitefeaturemeter.dom.YandexOAuthResponse;
import javagrinko.sitefeaturemeter.services.UserService;
import javagrinko.sitefeaturemeter.webapp.windows.LoginWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

@SpringUI
@Theme("valo")
public class MeterUI extends UI {

    @Autowired
    private LoginWindow loginWindow;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private UserService userService;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        initLogin();
    }

    private void initLogin() {
        User user = userService.getUser();
        if (user == null) {
            String uriFragment = getPage().getUriFragment();
            YandexOAuthResponse response = conversionService.convert(uriFragment, YandexOAuthResponse.class);
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
                        ((response.getState() == null) ? "" : ("\nСообщение: " + response.getState())) ,
                        Notification.Type.TRAY_NOTIFICATION).show(getPage());

            }
        }
    }
}
