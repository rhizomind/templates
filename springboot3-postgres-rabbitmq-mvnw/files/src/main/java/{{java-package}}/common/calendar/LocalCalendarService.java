package {{java-package}}.common.calendar;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class LocalCalendarService implements CalendarService {
    @Override
    public ZonedDateTime getNow() {
        return ZonedDateTime.now();
    }
}
