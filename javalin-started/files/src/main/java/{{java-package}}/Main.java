package {{java-package}};

import static java.util.concurrent.CompletableFuture.completedFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.json.JavalinJackson;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public class Main {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson(mapper, true));
        }).start(8080);

        app.get(
                "/async-hello",
                handleAsync((ctx) -> completedFuture(new MyObject("Async World!", 123)))
        );
    }

    @NotNull
    private static Handler handleAsync(
            Function<Context, CompletableFuture<MyObject>> myObjectSupplier) {
        return (ctx) ->
                ctx.future(() -> myObjectSupplier.apply(ctx).thenAccept(ctx::json));
    }
}
