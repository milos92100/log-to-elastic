package com.logtoelastic.sandbox.rabbitmq;

import com.logtoelastic.domain.authentication.AuthenticationCredentials;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.tools.jsonrpc.JsonRpcClient;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class JsonRpcTestClient {

    private static String requestQueueName = "json_rpc_queue";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");

        var connection = factory.newConnection();
        var channel = connection.createChannel();
//
        JsonRpcClient jsonRpcClient = new JsonRpcClient(channel, "", requestQueueName);
        jsonRpcClient.call("", new Object[]{new AuthenticationCredentials("", "")});
    }
}
