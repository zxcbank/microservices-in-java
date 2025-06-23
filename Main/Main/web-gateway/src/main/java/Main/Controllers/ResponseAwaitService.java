package Main.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ResponseAwaitService<R> {

    private final Map<String, CompletableFuture<List<R>>> pendingCollectionResponses = new ConcurrentHashMap<>();
    private final Map<String, CompletableFuture<R>> pendingResponses = new ConcurrentHashMap<>();

    @Value("${spring.kafka.timeout}")
    private Long timeout;

    public CompletableFuture<List<R>> waitForCollectionResponse(String correlationId) {
        CompletableFuture<List<R>> future = new CompletableFuture<>();
        pendingCollectionResponses.put(correlationId, future);

        future.orTimeout(timeout, TimeUnit.SECONDS).exceptionally(ex -> {
            pendingCollectionResponses.remove(correlationId);
            throw new RuntimeException("Timeout waiting for response");
        });

        return future;
    }

    public CompletableFuture<R> waitForElementResponse(String correlationId) {

        CompletableFuture<R> future = new CompletableFuture<>();
        pendingResponses.put(correlationId, future);

        future.orTimeout(timeout, TimeUnit.SECONDS).exceptionally(ex -> {
            pendingResponses.remove(correlationId);
            throw new RuntimeException("Timeout waiting for response");
        });

        return future;
    }

    public void completeResponse(String correlationId, List<R> response) {
        CompletableFuture<List<R>> future = pendingCollectionResponses.remove(correlationId);
        if (future != null) {
            future.complete(response);
        }
    }

    public void completeResponse(String correlationId, R response) {
        CompletableFuture<R> future = pendingResponses.remove(correlationId);

        if (future != null) {
            future.complete(response);
        }
    }
}
