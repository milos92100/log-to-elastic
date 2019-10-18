package com.example.logtoelastic;


import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;


import java.net.UnknownHostException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws UnknownHostException {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));


        CreateIndexRequest request = new CreateIndexRequest("twitter");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        request.setTimeout(TimeValue.timeValueMinutes(2));

        client.indices().createAsync(request, RequestOptions.DEFAULT, new ActionListener<CreateIndexResponse>() {
            public void onResponse(CreateIndexResponse createIndexResponse) {
                boolean acknowledged = createIndexResponse.isAcknowledged();
                boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
                System.out.println("acknowledged: " + acknowledged + "; shardsAcknowledged: " + shardsAcknowledged);
            }

            public void onFailure(Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
