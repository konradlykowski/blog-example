package com.konrad.examples.blog.posts.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.posts.input.validators.DefaultInputValidator;
import com.konrad.examples.blog.posts.input.validators.SearchByInputValidator;
import com.konrad.examples.blog.posts.input.validators.impl.SearchCategoryValidator;
import com.konrad.examples.blog.posts.repository.PostsRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class FilterPostsRequestHandler implements RequestStreamHandler {

    private final RestTemplate restTemplate;
    JSONParser parser = new JSONParser();


    public FilterPostsRequestHandler() {
        restTemplate = new RestTemplate();
    }

    public FilterPostsRequestHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        JSONObject responseJson = new JSONObject();
        try {
            JSONObject event = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
            JSONObject responseBody = new JSONObject();
            responseBody.put("posts", (new PostsRepository(new ElasticSearchClient(), new RestTemplate()).filterPostsBy(getFromParam(event), getWhereParam(event), getWhatParam(event))));
            responseJson.put("body", responseBody.toString());
        } catch (ParseException | IOException | NumberFormatException | NullPointerException e) {
            responseJson.put("statusCode", "400");
        } finally {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(responseJson.toJSONString());
            writer.close();
        }
    }

    private int getFromParam(JSONObject event) throws NumberFormatException, NullPointerException {
        return Integer.valueOf(((JSONObject) event.get("queryStringParameters")).get("from").toString());
    }

    private String getWhatParam(JSONObject event) throws ParseException, NullPointerException {
        return (new DefaultInputValidator().validate(((JSONObject) parser.parse(event.get("body").toString())).get("what").toString()));
    }

    private String getWhereParam(JSONObject event) throws ParseException, NullPointerException {
        return (new SearchByInputValidator().validate(((JSONObject) parser.parse(event.get("body").toString())).get("where").toString()));

    }


}
