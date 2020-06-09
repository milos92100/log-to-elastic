package com.logtoelastic.sandbox.logging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.UUID;

public class LoggingApp {

    private static final Logger logger = LogManager.getLogger(LoggingApp.class);

    public static void main(String[] args) {
        ///System.out.println("Hello from com.logtoelastic.sandbox.logging");

        int i = 0;
        while (i < 100) {
            ThreadContext.put("correlationId", UUID.randomUUID().toString());
            try {
                throw new Exception("Some shit happened");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            i++;
        }
    }
}
