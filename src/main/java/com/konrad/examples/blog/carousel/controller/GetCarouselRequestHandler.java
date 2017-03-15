package com.konrad.examples.blog.carousel.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.posts.repository.PostsRepository;
import com.konrad.examples.blog.response.writer.ResponseHeaderCorsDecorated;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class GetCarouselRequestHandler implements RequestStreamHandler {

    private final RestTemplate restTemplate;
    JSONParser parser = new JSONParser();


    public GetCarouselRequestHandler() {
        restTemplate = new RestTemplate();
    }

    public GetCarouselRequestHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        JSONObject responseJson = new JSONObject();
        try {
            JSONObject responseBody = new JSONObject();
            responseBody.put("carousel-posts", (new PostsRepository(new ElasticSearchClient(), new RestTemplate()).getLastThreeCarouselPosts()));
            responseJson.put("body", responseBody.toString());
        } catch (NullPointerException e) {
            responseJson.put("statusCode", "400");
        } finally {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            responseJson.put("headers", (new ResponseHeaderCorsDecorated()).getHeaders());
            writer.write(responseJson.toJSONString());
            writer.close();
        }
    }

}
