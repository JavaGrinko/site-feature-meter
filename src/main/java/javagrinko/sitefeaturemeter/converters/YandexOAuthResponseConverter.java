package javagrinko.sitefeaturemeter.converters;

import javagrinko.sitefeaturemeter.dom.yandex.OAuthResponse;
import org.springframework.core.convert.converter.Converter;

public class YandexOAuthResponseConverter implements Converter<String, OAuthResponse> {
    @Override
    public OAuthResponse convert(String source) {
        if (source == null){
            return null;
        }
        OAuthResponse OAuthResponse = new OAuthResponse();
        String[] parts = source.split("&");
        for (String part : parts) {
            String[] pair = part.split("=");
            String argument = pair[0];
            String value = pair[1];
            switch (argument){
                case "access_token":
                    OAuthResponse.setAccessToken(value);
                    break;
                case "token_type":
                    OAuthResponse.setTokenType(value);
                    break;
                case "expires_in":
                    OAuthResponse.setExpiresInSeconds(Long.parseLong(value));
                    break;
                case "state":
                    OAuthResponse.setState(value);
            }
        }
        return OAuthResponse;
    }
}
