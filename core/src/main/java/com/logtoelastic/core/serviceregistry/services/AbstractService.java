package com.logtoelastic.core.serviceregistry.services;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.logtoelastic.core.serviceregistry.nats.Envelope;
import com.logtoelastic.core.serviceregistry.nats.EnvelopeHeader;
import io.nats.client.Connection;
import org.apache.logging.log4j.ThreadContext;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Function;

public class AbstractService {

    private static final String CORRELATION_ID = "correlationId";
    private Connection connection;
    private Gson gson;

    private ExecutorService EXECUTOR =  //
            ForkJoinPool.commonPool().getParallelism() > 1 ? //
                    ForkJoinPool.commonPool() : Executors.newFixedThreadPool(4);

    public AbstractService(Connection connection) {
        this.connection = connection;
        this.gson = new Gson();
    }


    /**
     * Execute the given service request and returns the future result.
     * The request is published to a service provider asynchronously.
     *
     * @param request ServiceRequest< A, R>
     * @param <A>     Argument type
     * @param <R>     Result type
     * @return result Future
     */
    protected <A, R> CompletableFuture<R> executeRequest(ServiceRequest<A, R> request) {
        var envelope = new Envelope<>(
                new EnvelopeHeader(ThreadContext.get(CORRELATION_ID)),
                request.getArgument()
        );

        final var jsonRequest = gson.toJson(envelope);
        final var future = new CompletableFuture<R>();

        EXECUTOR.execute(() -> {
            try {
                var message = connection.request( //
                        request.getSubject(), //
                        jsonRequest.getBytes(StandardCharsets.UTF_8), //
                        Duration.ofSeconds(30) //
                );

                R result = gson.fromJson(
                        new String(message.getData()), //
                        request.getReturnValueClass() //
                );

                future.complete(result);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    /**
     * Registers a handler for the given service provider subject.
     *
     * @param subject      subject of the service provider
     * @param requestClass Request that should be handled
     * @param handler      Handler function
     * @param <A>          Request that is passed to handler function
     * @param <R>          Result that the handler should return
     */
    protected <A, R> void handleRequest(final String subject, Class<A> requestClass, Function<A, R> handler) {
        handleRequest(subject, requestClass, handler, EXECUTOR);
    }

    /**
     * Registers a handler for the given service provider subject.
     *
     * @param subject      subject of the service provider
     * @param requestClass Request that should be handled
     * @param handler      Handler function
     * @param <A>          Request that is passed to handler function
     * @param <R>          Result that the handler should return
     * @param executor     Executor for executing handler function
     */
    protected <A, R> void handleRequest(final String subject, Class<A> requestClass, Function<A, R> handler, Executor executor) {
        connection.createDispatcher((msg) -> executor.execute(() -> {
                    var typeToken = TypeToken.getParameterized(Envelope.class, requestClass).getType();
                    String requestJson = new String(msg.getData(), StandardCharsets.UTF_8);

                    Envelope<A> request = gson.fromJson(requestJson, typeToken);

                    ThreadContext.put(CORRELATION_ID, request.getHeader().getCorrelationId());

                    R result = handler.apply(request.getBody());

                    var replyJson = gson.toJson(result);
                    msg.getConnection().publish(msg.getReplyTo(), replyJson.getBytes(StandardCharsets.UTF_8));
                })
        ).subscribe(subject);
    }
}
