package com.logtoelastic.core.serviceregistry.services.impl;

public class ServiceRequest<A, R> {
    private String subject;
    private A argument;
    private Class<R> returnValueClass;

    private ServiceRequest() {
    }

    public ServiceRequest(String subject, A argument, Class<R> returnValueClass) {
        this.subject = subject;
        this.argument = argument;
        this.returnValueClass = returnValueClass;
    }

    public String getSubject() {
        return subject;
    }

    public A getArgument() {
        return argument;
    }

    public Class<R> getReturnValueClass() {
        return returnValueClass;
    }
}
