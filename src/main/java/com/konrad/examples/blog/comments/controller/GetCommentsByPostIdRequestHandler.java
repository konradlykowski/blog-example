package com.konrad.examples.blog.comments.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.konrad.examples.blog.comments.repository.CommentsRepository;
import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.posts.input.validators.DefaultInputValidator;
import com.konrad.examples.blog.posts.repository.PostsRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class GetCommentsByPostIdRequestHandler implements RequestStreamHandler {

    private final RestTemplate restTemplate;
    JSONParser parser = new JSONParser();


    public GetCommentsByPostIdRequestHandler() {
        restTemplate = new RestTemplate();
    }

    public GetCommentsByPostIdRequestHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        JSONObject responseJson = new JSONObject();
        try {
            JSONObject event = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
            JSONObject responseBody = new JSONObject();
            responseBody.put("comments", (new CommentsRepository(new ElasticSearchClient(), new RestTemplate()).getCommentsByPostId(getPostId(event))));
            responseJson.put("body", responseBody.toString());
        } catch (ParseException | IOException | NullPointerException e) {
            responseJson.put("statusCode", "400");
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
