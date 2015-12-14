package javagrinko.sitefeaturemeter.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class YandexJsonDateDeserializer extends JsonDeserializer {
    public static final String PATTERN = "yyyyMMdd";

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);

    @Override
    public Object deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String valueAsString = p.getValueAsString();
        try {
            return simpleDateFormat.parse(valueAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
