package {{java-package}}.interfaces.rest;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Slf4j
public class AsyncFileReaderBytes {

    private AsyncFileReaderBytes() {
    }

    public static CompletableFuture<Integer> copyAllBytes(
            AsynchronousFileChannel asyncFile,
            ByteBuffer buffer,
            int position,
            OutputStream out) {
        return copyBytes(asyncFile, buffer, position, out)
                .thenCompose(index ->
                        index < 0
                                ? completedFuture(position)
                                : copyAllBytes(asyncFile, buffer.clear(), position + index, out));

    }

    private static CompletableFuture<Integer> copyBytes(
            AsynchronousFileChannel asyncFile,
            ByteBuffer buffer,
            int position,
            OutputStream out) {
        CompletableFuture<Integer> promise = new CompletableFuture<>();
        asyncFile.read(
                buffer,
                position,
                buffer,
                new CompletionHandler<>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        try {
                            if (result > 0) {
                                attachment.flip();
                                byte[] data = new byte[attachment.limit()]; // limit = result
                                attachment.get(data);
                                write(out, data);
                            }
                            promise.complete(result);
                        } catch (Exception e) {
                            log.warn("Unexpected exception while transferring data to output stream", e);
                            promise.completeExceptionally(e);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        promise.completeExceptionally(exc);
                    }
                });
        return promise;
    }

    private static void write(OutputStream out, byte[] data) {
        try {
            out.write(data);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
