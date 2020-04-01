package com.logtoelastic.core.serviceregistry.nats;

public class EnvelopeHeader {
    private String correlationId;

    public EnvelopeHeader(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}
