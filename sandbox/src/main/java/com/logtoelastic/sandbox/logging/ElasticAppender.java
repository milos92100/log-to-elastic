package com.logtoelastic.sandbox.logging;

import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Plugin(
        name = "ElasticAppender",
        category = "Core",
        elementType = "appender",
        printObject = true
)
public class ElasticAppender extends AbstractAppender {
    RestHighLevelClient client = null;
    Gson gson = null;

    protected ElasticAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, String host, int port, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
        if (host.isEmpty()) {
            throw new IllegalArgumentException("host must not be empty");
        }
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
        gson = new Gson();
    }

    @PluginFactory
    public static ElasticAppender createAppender(@PluginAttribute("name") String name,
                                                 @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                                 @PluginAttribute("host") String host,
                                                 @PluginAttribute("port") int port,
                                                 @PluginElement("Layout") Layout layout,
                                                 @PluginElement("Filters") Filter filter) {

        return new ElasticAppender(name, filter, layout, false, host, port, new Property[]{});
    }


    public void append(LogEvent logEvent) {
        LocalDateTime.now();
        GetIndexRequest checkIndexRequest = new GetIndexRequest("twitter");
        try {
            boolean exists = client.indices().exists(checkIndexRequest, RequestOptions.DEFAULT);
            if (exists) {
                var json = gson.toJson(logEvent);

                IndexRequest saveEventRequest = new IndexRequest("twitter");
                saveEventRequest.id(UUID.randomUUID().toString());
                saveEventRequest.source(json, XContentType.JSON);
                saveEventRequest.timeout(TimeValue.timeValueSeconds(10));
                saveEventRequest.opType(DocWriteRequest.OpType.CREATE);

                IndexResponse indexResponse = client.index(saveEventRequest, RequestOptions.DEFAULT);
                if (indexResponse.status() != RestStatus.ACCEPTED && indexResponse.status() != RestStatus.CREATED) {
                    System.out.println("Failed to store log event: " + indexResponse.status());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
