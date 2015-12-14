package javagrinko.sitefeaturemeter.webapp;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javagrinko.sitefeaturemeter.dom.User;
import javagrinko.sitefeaturemeter.dom.yandex.Attendance;
import javagrinko.sitefeaturemeter.dom.yandex.AttendanceData;
import javagrinko.sitefeaturemeter.dom.yandex.OAuthResponse;
import javagrinko.sitefeaturemeter.services.UserService;
import javagrinko.sitefeaturemeter.services.YandexService;
import javagrinko.sitefeaturemeter.webapp.windows.LoginWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringUI
@Theme("valo")
@Widgetset("webapp.Widgetset")
public class MeterUI extends UI {

    @Autowired
    private HttpSession session;

    @Autowired
    private LoginWindow loginWindow;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private UserService userService;

    @Autowired
    private YandexService yandexService;
    private Chart chart;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        chart = new Chart();
        initLogin();
        verticalLayout.addComponent(chart);
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
                session.setAttribute("token", response.getAccessToken());
            }
        } else {
            session.setAttribute("token", user.getToken());
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                List<Attendance> attendances = yandexService.getAttendances(dateFormat.parse("20141210"), dateFormat.parse("20151210"), yandexService.getCounters().getCounters().get(1).getId());
                DataSeries dataSeries = new DataSeries();
                for (Attendance attendance : attendances) {
                    List<AttendanceData> data = attendance.getData();
                    for (AttendanceData attendanceData : data) {
                        dataSeries.add(new DataSeriesItem(attendanceData.getDate(), attendanceData.getVisitors()));
                    }
                }
                Configuration configuration = new Configuration();
                configuration.addSeries(dataSeries);
                chart.setConfiguration(configuration);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}