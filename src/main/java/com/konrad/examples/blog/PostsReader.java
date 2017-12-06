package com.konrad.examples.blog;

import org.springframework.web.client.RestTemplate;

public class PostsReader {
    private final String elasticSearchURL = "http://localhost:9200";
    private final RestTemplate restTemplate;

    public PostsReader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAllPosts(int from) {
        String query = "{\n" +
                "\"from\" : " + from + ", \"size\" : 7," +
                "    \"query\": {\n" +
                "\"match_all\" : {}" +
                "    },\n" +
                "    \"sort\": { \"date\": { \"order\": \"desc\" }}\n" +
                "} ";
        return restTemplate.postForEntity(elasticSearchURL + "/posts/_search", query, String.class).getBody();
    }

    public String getPostsByCategory(int from, String category) {
        String query = "{\n" +
                "\"from\" : " + from + ", \"size\" : 7," +
                "    \"query\": {\n" +
                "\"bool\": {\"should\": [{ \"match\": { \"category\" : \"" + category + "\"} }]}" +
                "    },\n" +
                "    \"sort\": { \"date\": { \"order\": \"desc\" }}\n" +
                "} ";
        return restTemplate.postForEntity(elasticSearchURL + "/posts/_search", query, String.class).getBody();
    }

    public String getPostsByTag(int from, String tag) {
        String query = "{\n" +
                "\"from\" : " + from + ", \"size\" : 7," +
                "    \"query\": {\n" +
                "\"bool\": {\"should\": [{ \"match\": { \"tags\" : \"" + tag + "\"} }]}" +
                "    },\n" +
                "    \"sort\": { \"date\": { \"order\": \"desc\" }}\n" +
                "} ";
        return restTemplate.postForEntity(elasticSearchURL + "/posts/_search", query, String.class).getBody();
    }

    public String getPostsByQuery(int from, String searchQuery) {
        String query = "{\n" +
                "\"from\" : " + from + ", \"size\" : 7," +
                "    \"query\": {\n" +
                "\"bool\": {\"should\": [" +
                "{ \"match\": { \"content\" : \"" + searchQuery + "\"} }," +
                "{ \"match\": { \"tags\" : \"" + searchQuery + "\"} }," +
                "{ \"match\": { \"description\" : \"" + searchQuery + "\"} }" +
                "]}" +
                "    },\n" +
                "    \"sort\": { \"date\": { \"order\": \"desc\" }}\n" +
                "} ";
        return restTemplate.postForEntity(elasticSearchURL + "/posts/_search", query, String.class).getBody();
    }


    public String getPostsByLocation(Integer from, String location) {
        String query = "{\n" +
                "\"from\" : " + from + ", \"size\" : 7," +
                "    \"query\": {\n" +
                "\"bool\": {\"should\": [{ \"match\": { \"location\" : \"" + location + "\"} }]}" +
                "    },\n" +
                "    \"sort\": { \"date\": { \"order\": \"desc\" }}\n" +
                "} ";
        return restTemplate.postForEntity(elasticSearchURL + "/posts/_search", query, String.class).getBody();
    }
}