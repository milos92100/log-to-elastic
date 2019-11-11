package com.logtoelastic.sandbox.logging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.UUID;

public class LoggingApp {

    private static final Logger logger = LogManager.getLogger(LoggingApp.class);

    public static void main(String[] args) {
        ///System.out.println("Hello from com.logtoelastic.sandbox.logging");

        ThreadContext.put("correlationId", UUID.randomUUID().toString());

        logger.error("Hello {}", "World");

    }
}
