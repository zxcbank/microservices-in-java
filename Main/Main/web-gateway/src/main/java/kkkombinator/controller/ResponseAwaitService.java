package kkkombinator.controller;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class ResponseAwaitService<R> {
    private final Map<String, CompletableFuture<List<R>>> pendingResponses = new ConcurrentHashMap<>();

    public CompletableFuture<List<R>> waitForResponse(String correlationId) {
        CompletableFuture<List<R>> future = new CompletableFuture<>();
        pendingResponses.put(correlationId, future);

        future.orTimeout(5, TimeUnit.SECONDS)
                .exceptionally(ex -> {
                    pendingResponses.remove(correlationId);
                    throw new RuntimeException("Timeout waiting for response");
                });

        return future;
    }

    public void completeResponse(String correlationId, List<R> response) {
        CompletableFuture<List<R>> future = pendingResponses.remove(correlationId);
        if (future != null) {
            future.complete(response);
        }
    }
}
