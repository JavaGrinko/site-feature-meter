package javagrinko.sitefeaturemeter.converters;

import javagrinko.sitefeaturemeter.dom.YandexOAuthResponse;
import org.springframework.core.convert.converter.Converter;

public class YandexOAuthResponseConverter implements Converter<String, YandexOAuthResponse> {
    @Override
    public YandexOAuthResponse convert(String source) {
        if (source == null){
            return null;
        }
        YandexOAuthResponse yandexOAuthResponse = new YandexOAuthResponse();
        String[] parts = source.split("&");
        for (String part : parts) {
            String[] pair = part.split("=");
            String argument = pair[0];
            String value = pair[1];
            switch (argument){
                case "access_token":
                    yandexOAuthResponse.setAccessToken(value);
                    break;
                case "token_type":
                    yandexOAuthResponse.setTokenType(value);
                    break;
                case "expires_in":
                    yandexOAuthResponse.setExpiresInSeconds(Long.parseLong(value));
                    break;
                case "state":
                    yandexOAuthResponse.setState(value);
            }
        }
        return yandexOAuthResponse;
    }
}
