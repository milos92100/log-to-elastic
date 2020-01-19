package com.logtoelastic.sandbox.rabbitmq;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BooleanSupplier;

public class Producer {

    private static final Logger logger = LogManager.getLogger(Producer.class);

    private static final String EXCHANGE_NAME = "create-account-command";
    private static final int MESSAGE_COUNT = 10000;

    public static void main(String[] args) {
        try {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("admin");
            factory.setPassword("admin");
            factory.setConnectionTimeout(60000);

            try (Connection connection = factory.newConnection()) {
                Channel channel = connection.createChannel();
                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
                channel.confirmSelect();

                ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

                ConfirmCallback cleanOutstandingConfirms = (sequenceNumber, multiple) -> {
                    if (multiple) {
                        ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(
                                sequenceNumber, true
                        );
                        confirmed.clear();
                    } else {
                        outstandingConfirms.remove(sequenceNumber);
                    }
                };

                channel.addConfirmListener(cleanOutstandingConfirms, (sequenceNumber, multiple) -> {
                    String body = outstandingConfirms.get(sequenceNumber);
                    System.err.format(
                            "Message with body %s has been nack-ed. Sequence number: %d, multiple: %b%n",
                            body, sequenceNumber, multiple
                    );
                    cleanOutstandingConfirms.handle(sequenceNumber, multiple);
                });

                long start = System.nanoTime();
                for (int i = 0; i < MESSAGE_COUNT; i++) {
                    String message = "message-" + i;
                    outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
                    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
                    //Thread.sleep(500L);
                }

                if (!waitUntil(Duration.ofSeconds(100), outstandingConfirms::isEmpty)) {
                    throw new IllegalStateException("All messages could not be confirmed in 60 seconds");
                }

                long end = System.nanoTime();
                System.out.format("Published %,d messages and handled confirms asynchronously in %,d ms%n", MESSAGE_COUNT, Duration.ofNanos(end - start).toMillis());
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }

    static boolean waitUntil(Duration timeout, BooleanSupplier condition) throws InterruptedException {
        int waited = 0;
        while (!condition.getAsBoolean() && waited < timeout.toMillis()) {
            Thread.sleep(100L);
            waited = +100;
        }
        return condition.getAsBoolean();
    }
}
