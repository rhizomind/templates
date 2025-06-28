package {{java-package}}.application;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Supplier;

public class CustomObjectMapperSupplier implements Supplier<ObjectMapper> {
    @Override
    public ObjectMapper get() {
        return ObjectMapperCustomizer.createCustomized();
    }
}
