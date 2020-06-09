package com.logtoelastic.library.logging;

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
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@Plugin(
        name = "ElasticAppender",
        category = "Core",
        elementType = "appender",
        printObject = true
)
public class ElasticAppender extends AbstractAppender {
    private RestHighLevelClient client;
    private Gson gson;
    private String lastIndex;


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
        var index = getCurrentIndexDate();

        try {
            if (lastIndex == null || !lastIndex.equals(index)) {
                if (!indexExists(index)) {
                    createIndex(index);
                }
            }

            sendRecord(mapToRecord(logEvent), index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean indexExists(String index) throws IOException {
        return client.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
    }

    private void sendRecord(LogRecord record, String index) throws IOException {
        var json = gson.toJson(record);

        IndexRequest saveEventRequest = new IndexRequest(index);
        saveEventRequest.id(UUID.randomUUID().toString());
        saveEventRequest.source(json, XContentType.JSON);
        saveEventRequest.timeout(TimeValue.timeValueSeconds(10));
        saveEventRequest.opType(DocWriteRequest.OpType.CREATE);

        var response = client.index(saveEventRequest, RequestOptions.DEFAULT);
        if (response.status() != RestStatus.ACCEPTED && response.status() != RestStatus.CREATED) {
            System.err.println("Failed to store log event: " + response.status());
        }
    }

    private void createIndex(String index) throws IOException {
        var createIndex = new CreateIndexRequest(index);
        createIndex.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        createIndex.setTimeout(TimeValue.timeValueMinutes(2));

        var response = client.indices().create(createIndex, RequestOptions.DEFAULT);
        if (!response.isAcknowledged()) {
            System.err.println("ElasticAppender - not yet acknowledged " + response);
        } else {
            lastIndex = index;
        }
    }

    private String getCurrentIndexDate() {
        var now = ZonedDateTime.now();

        StringBuilder sb = new StringBuilder();
        sb.append("log_");
        sb.append(now.getDayOfMonth());
        sb.append("-");
        sb.append(now.getMonthValue());
        sb.append("-");
        sb.append(now.getYear());

        return sb.toString();
    }

    private LogRecord mapToRecord(LogEvent logEvent) {
        var record = new LogRecord();
        record.setCreationTime(Instant.ofEpochMilli(logEvent.getTimeMillis()));
        record.setCreationTimeFormatted(record.getCreationTime().toString());
        record.setException(logEvent.getThrown());
        record.setLevel(logEvent.getLevel().name());
        record.setLoggerName(logEvent.getLoggerName());
        record.setThreadName(logEvent.getThreadName());
        record.setThreadId(logEvent.getThreadId());
        record.setThreadPriority(logEvent.getThreadPriority());
        record.setMessage(logEvent.getMessage().getFormattedMessage());
        record.setCorrelationId(logEvent.getContextData().getValue("correlationId"));
        record.setContextData(logEvent.getContextData().toMap());
        record.setContextStack(logEvent.getContextStack().asList());
        return record;
    }
}
