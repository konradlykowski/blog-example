package com.konrad.examples.blog.posts.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.posts.input.validators.DefaultInputValidator;
import com.konrad.examples.blog.posts.repository.PostsRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class GetPostByIdRequestHandler implements RequestStreamHandler {

    private final RestTemplate restTemplate;
    JSONParser parser = new JSONParser();


    public GetPostByIdRequestHandler() {
        restTemplate = new RestTemplate();
    }

    public GetPostByIdRequestHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        JSONObject responseJson = new JSONObject();
        try {
            JSONObject event = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
            JSONObject responseBody = new JSONObject();
            responseBody.put("post", (new PostsRepository(new ElasticSearchClient(), new RestTemplate()).getPostById(getPostId(event))));

            responseBody.put("post", "posts/post/" + (new RestTemplate()).getForEntity("https://vpc-iglawpodrozy-6xrukndwc6cyellfdhpfu4y6ma.eu-central-1.es.amazonaws.com/posts/post/swiateczny-zurich" , String.class).getBody());
            responseJson.put("body", responseBody.toString());
        } catch (Exception e) {
            responseJson.put("statusCode", "400");
            responseJson.put("error", e.getMessage());
        } finally {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(responseJson.toJSONString());
            writer.close();
        }
    }

    private String getPostId(JSONObject event) throws NullPointerException {
        return (new DefaultInputValidator()).validate(((JSONObject) event.get("pathParameters")).get("postId").toString());
    }


}
