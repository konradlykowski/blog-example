package com.konrad.examples.blog.posts.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.posts.input.validators.DefaultInputValidator;
import com.konrad.examples.blog.posts.repository.PostsRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Map;

public class GetPostByIdRequestHandler2 implements RequestHandler<Map<String, String>, String> {

    private final RestTemplate restTemplate;
    JSONParser parser = new JSONParser();


    public GetPostByIdRequestHandler2() {
        restTemplate = new RestTemplate();
    }

    public GetPostByIdRequestHandler2(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public static void main(String[] args) {
        (new GetPostByIdRequestHandler2()).handleRequest(null, null);
    }

    @Override
    public String handleRequest(Map<String, String> stringStringMap, Context context) {
        //String koko = (new RestTemplate()).getForEntity(, String.class).getBody();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity("", headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://vpc-elasticigla-j4uv6iwavksdnr5rvrfxs7plei.eu-central-1.es.amazonaws.com/posts/post/geometryczna-suknia-nowa.html",
                HttpMethod.GET, entity, String.class);
        System.out.println(response.toString());
        return response.toString();
    }
}
