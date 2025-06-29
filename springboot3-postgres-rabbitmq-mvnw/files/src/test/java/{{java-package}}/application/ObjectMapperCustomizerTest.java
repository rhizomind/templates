package {{java-package}}.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class ObjectMapperCustomizerTest {

    @Test
    void should_serialize_zonedDatetime_as_String() throws JsonProcessingException {
        String s = ObjectMapperCustomizer.createCustomized().writeValueAsString(ZonedDateTime.of(2025, 6, 21, 1, 1, 1, 1, ZoneId.of("UTC")));

        Assertions.assertEquals("\"2025-06-21T01:01:01.000000001Z\"", s);
    }

    @Test
    void should_serialize_localDatetime_as_String() throws JsonProcessingException {
        String s = ObjectMapperCustomizer.createCustomized().writeValueAsString(LocalDateTime.of(2025, 6, 21, 1, 1, 1, 1));

        Assertions.assertEquals("\"2025-06-21T01:01:01.000000001\"", s);
    }
}
