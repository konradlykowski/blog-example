package com.konrad.examples.blog.comments.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konrad.examples.blog.comments.domain.Comment;
import com.konrad.examples.blog.comments.repository.CommentsRepository;
import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.posts.input.validators.CommentsInputValidator;
import com.konrad.examples.blog.posts.input.validators.DefaultInputValidator;
import com.konrad.examples.blog.response.writer.ResponseHeaderCorsDecorated;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class AddCommentRequestHandler implements RequestStreamHandler {

    private final RestTemplate restTemplate;
    JSONParser parser = new JSONParser();


    public AddCommentRequestHandler() {
        restTemplate = new RestTemplate();
    }

    public AddCommentRequestHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        JSONObject responseJson = new JSONObject();
        try {
            JSONObject event = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
            JSONObject responseBody = new JSONObject();
            responseBody.put("comments", (new CommentsRepository(new ElasticSearchClient(), new RestTemplate()).addComment(getComment(event))));
            responseJson.put("body", responseBody.toString());
        } catch (ParseException | IOException | NullPointerException e) {
            responseJson.put("statusCode", "400");
            responseJson.put("error", e.getMessage());
        } finally {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            responseJson.put("headers", (new ResponseHeaderCorsDecorated()).getHeaders());
            writer.write(responseJson.toJSONString());
            writer.close();
        }
    }

    private Comment getComment(JSONObject event) throws ParseException, NullPointerException, IOException {
        return (new CommentsInputValidator()).validate((new ObjectMapper()).readValue((((JSONObject) parser.parse(event.get("body").toString())).get("comment").toString()), Comment.class));
    }


}
