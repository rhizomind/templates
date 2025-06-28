package {{java-package}}.interfaces.rest;

import static java.nio.channels.AsynchronousFileChannel.open;
import static {{java-package}}.interfaces.rest.AsyncFileReaderBytes.copyAllBytes;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.util.zip.GZIPOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HelloWorldResource {

    private final ObjectMapper objectMapper;

    @GetMapping(value = "categories")
    public String getCategories() {
        return "hello";
    }

    static int indexer = 0;

    private void returnResponseAsync(HttpServletRequest httpServletRequest, File resultFile, String eTag) throws IOException {
        final int id = indexer++;

        httpServletRequest.startAsync();
        AsyncContext asyncContext = httpServletRequest.getAsyncContext();
        HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
        response.setHeader("ETag", eTag);
        response.setHeader("Content-Encoding", "gzip");

        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        AsynchronousFileChannel open = open(resultFile.toPath());
        log.debug("starting transfer (" + Files.size(resultFile.toPath()) + "B): " + id);
        var outputStream = acceptsGzipEncoding(httpServletRequest)
                ? new GZIPOutputStream(response.getOutputStream())
                : response.getOutputStream();
        copyAllBytes(
                open,
                ByteBuffer.allocate(32768),
                0,
                outputStream
        ).whenCompleteAsync((pos, ex) -> {
            if (ex != null) {
                log.error("exception while returning betting data", ex);
            }
            log.debug("completed transfer (" + pos + "B): " + id);
            try {
                if (outputStream instanceof GZIPOutputStream g) {
                    g.finish();
                }
                outputStream.flush();
            } catch (Exception e) {
                log.error("unmable to close servletoutputstream", e);
            }
            try {
                open.close();
            } catch (IOException e) {
                log.error("unmable to close AsyncFileChannel", e);
            }
            asyncContext.complete();
            resultFile.delete();
        });
    }

    private boolean acceptsGzipEncoding(HttpServletRequest httpRequest) {
        String acceptEncoding = httpRequest.getHeader("Accept-Encoding");
        return acceptEncoding != null && acceptEncoding.contains("gzip");
    }

    private static String parseClientTag(HttpServletRequest httpServletRequest) {
        String clientTag = null;
        try {
            clientTag = httpServletRequest.getHeader("if-none-match");
        } catch (Exception e) {
            log.warn("unable to parse tag", e);
        }
        return clientTag;
    }

}
