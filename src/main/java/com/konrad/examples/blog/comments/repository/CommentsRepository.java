package com.konrad.examples.blog.comments.repository;

import com.konrad.examples.blog.comments.domain.Comment;
import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.elasticsearch.query.ElasticSearchQueryBuilder;
import com.konrad.examples.blog.posts.filter.FilterFields;
import org.springframework.web.client.RestTemplate;

public class CommentsRepository {
    ElasticSearchClient elasticSearchClient;

    public CommentsRepository(ElasticSearchClient elasticSearchClient, RestTemplate restTemplate) {
        this.elasticSearchClient = elasticSearchClient;
        this.elasticSearchClient.executeOn(restTemplate);
    }

    public String getCommentsByPostId(String postId) {
        FilterFields[] searchBy = new FilterFields[]{FilterFields.postId};
        return elasticSearchClient.executeQuery("/comments/_search", (new ElasticSearchQueryBuilder(0, searchBy, postId,500)).getQuery());
    }

    public String addComment(Comment comment) {
        return elasticSearchClient.executeQuery("/comments/comment", (new ElasticSearchQueryBuilder(comment)).getCommentQuery());
    }

}