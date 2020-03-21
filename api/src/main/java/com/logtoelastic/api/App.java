package com.logtoelastic.api;

import com.logtoelastic.core.serviceregistry.Registry;
import com.logtoelastic.core.serviceregistry.services.AuthenticationService;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class App {

    public static void main(String... args) throws IOException, TimeoutException {
//        InputStream propertiesStream = Objects.requireNonNull(App.class.getClassLoader().getResourceAsStream("app.properties"));
//
//        Properties properties = new Properties();
//        properties.load(propertiesStream);

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(App.class) //
                .web(WebApplicationType.SERVLET) //
                .registerShutdownHook(true) //
                .run(args);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");

        AuthenticationService service = Registry.createAuthenticationService(factory.newConnection());
        ctx.getBeanFactory().registerSingleton("core.authenticationService", service);

    }


}
