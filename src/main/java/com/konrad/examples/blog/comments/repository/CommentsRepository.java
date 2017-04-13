package com.konrad.examples.blog.comments.controller.repository;

import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.elasticsearch.query.ElasticSearchQueryBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;

public class CommentsRepository {
    private final RestTemplate restTemplate;
    ElasticSearchClient elasticSearchClient;

    public CommentsRepository(ElasticSearchClient elasticSearchClient, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.elasticSearchClient = elasticSearchClient;
    }

    public String getCommentsByPostId(String postId) {
        return elasticSearchClient.executeQuery("/comments/_search", (new ElasticSearchQueryBuilder(0, new LinkedList<String>() {
            {
                add("postId");
            }
        }, postId)).getQuery(), String.class).getBody();
    }

}