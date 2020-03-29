package com.logtoelastic.authentication;


import com.google.gson.Gson;
import com.logtoelastic.domain.User;
import com.logtoelastic.domain.authentication.AuthenticationResult;
import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        Connection nc = null;
        Gson gson = new Gson();
        try {

            InputStream propertiesStream = Objects.requireNonNull( //
                    App.class.getClassLoader().getResourceAsStream("app.properties") //
            );

            Properties properties = new Properties();
            properties.load(propertiesStream);

            var connectionString = properties.getProperty("natsConnectionString");

            var options = new Options.Builder()
                    .server(connectionString)
                    .connectionListener((connection, events) -> {
                        logger.info("status: {}, {}", connection.getStatus(), events);
                    }).build();

            nc = Nats.connect(options);
        } catch (InterruptedException | IOException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }

        nc.createDispatcher((msg) -> {
            String request = new String(msg.getData(), StandardCharsets.UTF_8);
            logger.info("request: {}", request);

            AuthenticationResult result = new AuthenticationResult(
                    new User(UUID.randomUUID().toString(), "foo", "john", "smith"),
                    "token-xxxx",
                    new String[]{"USER", "SALES_ADMIN"}
            );
            var replyJson = gson.toJson(result);

            msg.getConnection().publish(msg.getReplyTo(), replyJson.getBytes(StandardCharsets.UTF_8));
        }).subscribe("authenticate");
    }
}
