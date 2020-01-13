package com.logtoelastic.api.service;

import com.logtoelastic.api.dto.Greeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    private static final Logger logger = LogManager.getLogger(GreetingService.class);

    public Greeting createGreeting(Long cnt, String name) {
        logger.info("createGreeting: {} {}", cnt, name);
        return new Greeting(cnt, name);
    }
}
