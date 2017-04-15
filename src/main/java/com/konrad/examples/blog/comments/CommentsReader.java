package com.konrad.examples.blog.comments;

import org.springframework.web.client.RestTemplate;

public class CommentsReader {
    private final String elasticSearchURL = "http://localhost:9200";
    private final RestTemplate restTemplate;

    public CommentsReader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getComments(String postId) {
        String query = "{\n" +
                "    \"query\": {\n" +
                "\"bool\": {\"should\": [{ \"match\": { \"postId\" : \"" + postId + "\"} }]}" +
                "    },\n" +
                "    \"sort\": { \"date\": { \"order\": \"desc\" }}\n" +
                "} ";
        return restTemplate.postForEntity(elasticSearchURL + "/comments/_search", query, String.class).getBody();
    }

}