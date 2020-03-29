package com.logtoelastic.api;

import com.logtoelastic.core.serviceregistry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.context.ConfigurableApplicationContext;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@SpringBootApplication
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String... args) {
        try {
            InputStream propertiesStream = Objects.requireNonNull( //
                    App.class.getClassLoader().getResourceAsStream("app.properties") //
            );

            Properties properties = new Properties();
            properties.load(propertiesStream);

            var coreServiceFactory = Registry.createServiceFactory(properties);

            ConfigurableApplicationContext ctx = new SpringApplicationBuilder(App.class) //
                    .web(WebApplicationType.SERVLET) //
                    .registerShutdownHook(true) //
                    .run(args);

            ctx.getBeanFactory().registerSingleton("core.serviceFactory", coreServiceFactory);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }


}
