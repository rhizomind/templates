package {{java-package}}.interfaces.rest;


import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
/**
 * Created by tomasz on 6/17/2015.
 */
public class RestPreconditions {

    public static <T> T checkFound(Optional<T> optional) {
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
