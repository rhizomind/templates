package {{java-package}}.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@Component
public class ObjectMapperCustomizer implements Jackson2ObjectMapperBuilderCustomizer {

    public static ObjectMapper createCustomized() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        new ObjectMapperCustomizer().customize(builder);
        return builder.build();
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        builder
                .featuresToDisable(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE
                )
                .featuresToEnable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        SerializationFeature.FAIL_ON_EMPTY_BEANS,
                        DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                        SerializationFeature.WRITE_ENUMS_USING_TO_STRING
                )
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .modules(
                        javaTimeModule(),
                        new Jdk8Module(),
                        new ParameterNamesModule()
                );

    }

    private static JavaTimeModule javaTimeModule() {
        DateTimeFormatter serializationFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                .appendFraction(ChronoField.NANO_OF_SECOND, 3, 9, true)
                .appendOffset("+HHmm", "Z")
                .toFormatter();
        DateTimeFormatter deserializationFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                .appendOffset("+HHmm", "Z")
                .toFormatter();


        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(ZonedDateTime.class, new CustomerSerializer(serializationFormatter));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));

        javaTimeModule.addDeserializer(ZonedDateTime.class, new CustomDeserializer(deserializationFormatter));

        return javaTimeModule;
    }


    public static class CustomerSerializer extends JsonSerializer<ZonedDateTime> {

        private DateTimeFormatter formatter;

        public CustomerSerializer(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider)
                throws IOException {
            jsonGenerator.writeString(this.formatter.format(zonedDateTime));
        }
    }

    public static class CustomDeserializer extends JsonDeserializer<ZonedDateTime> {

        private DateTimeFormatter formatter;

        public CustomDeserializer(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public ZonedDateTime deserialize(JsonParser parser, DeserializationContext context)
                throws IOException {
            return ZonedDateTime.parse(parser.getText(), this.formatter);
        }
    }
}
