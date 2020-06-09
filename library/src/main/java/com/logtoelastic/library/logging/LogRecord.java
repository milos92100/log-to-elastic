package com.logtoelastic.library.logging;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class LogRecord {
    private String correlationId;
    private int threadPriority;
    private String threadName;
    private long threadId;
    private String message;
    private String loggerName;
    private String level;
    private Map<String, String> contextData;
    private List<String> contextStack;
    private String creationTimeFormatted;
    private Instant creationTime;
    private Throwable exception;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public int getThreadPriority() {
        return threadPriority;
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Map<String, String> getContextData() {
        return contextData;
    }

    public void setContextData(Map<String, String> contextData) {
        this.contextData = contextData;
    }

    public List<String> getContextStack() {
        return contextStack;
    }

    public void setContextStack(List<String> contextStack) {
        this.contextStack = contextStack;
    }

    public String getCreationTimeFormatted() {
        return creationTimeFormatted;
    }

    public void setCreationTimeFormatted(String creationTimeFormatted) {
        this.creationTimeFormatted = creationTimeFormatted;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
