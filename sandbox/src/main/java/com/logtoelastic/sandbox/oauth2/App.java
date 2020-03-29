package com.logtoelastic.sandbox.oauth2;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class App {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {


        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.googleapis.com/oauth2/v2/userinfo"))
                .setHeader("Authorization", "Bearer ya29.a0Adw1xeVIiXh1BPHDHLDvKSrPHLRJlpRg3n_Jx5e0x9hFi1zIv-C32qNrBjILGxl31Zt6aH9D3TG2UyfP1Aobub_UL8zHq2b2Yw_-cnz13gKKlK1opQGbgqHHhwzg__N9Timnni_o-keb9bCvY2lFvbOSEuzIZ2QEYms")
                .build();

        Future<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.get().statusCode());

        // print response body
        System.out.println(response.get().body());



    }
}
