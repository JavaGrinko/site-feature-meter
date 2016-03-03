package javagrinko.sitefeaturemeter.dom.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javagrinko.sitefeaturemeter.converters.YandexJsonDateDeserializer;

import java.util.Date;

public class Account {

    @JsonProperty("user_login")
    String userLogin;

    @JsonProperty("created_at")
    @JsonDeserialize(using = YandexJsonDateDeserializer.class)
    Date createdAt;
}
