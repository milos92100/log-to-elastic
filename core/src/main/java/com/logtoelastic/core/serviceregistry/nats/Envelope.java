package com.logtoelastic.core.serviceregistry.nats;

public class Envelope<T> {
    private EnvelopeHeader header;
    private T body;

    public Envelope(EnvelopeHeader header, T body) {
        this.header = header;
        this.body = body;
    }

    public EnvelopeHeader getHeader() {
        return header;
    }

    public T getBody() {
        return body;
    }
}
