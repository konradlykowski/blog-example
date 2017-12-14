package com.konrad.examples.blog.posts.repository;

import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.elasticsearch.query.ElasticSearchQueryBuilder;
import com.konrad.examples.blog.posts.filter.FilterFields;
import com.konrad.examples.blog.posts.filter.adapter.FilterFieldsToESFieldsAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.RestTemplate;

public class PostsRepository {

    private final static Log LOG = LogFactory.getLog(ElasticSearchClient.class);

    ElasticSearchClient elasticSearchClient;


    public PostsRepository(ElasticSearchClient elasticSearchClient, RestTemplate restTemplate) {
        this.elasticSearchClient = elasticSearchClient;
        this.elasticSearchClient.executeOn(restTemplate);
    }

    public String getAllPosts(int from) {
        LOG.info((new ElasticSearchQueryBuilder(from)).getQuery());
        return elasticSearchClient.executeQuery("posts/_search", (new ElasticSearchQueryBuilder(from)).getQuery());
    }

    public String getPostById(String postId) {
        return elasticSearchClient.executeGet("posts/post/" + postId);
    }

    public String filterPostsBy(int from, String by, String what) {
        return elasticSearchClient.executeQuery("posts/_search", (new ElasticSearchQueryBuilder(from, (new FilterFieldsToESFieldsAdapter(by)).getESFields(), what)).getQuery());
    }

    public String getLastThreeCarouselPosts() {
        return elasticSearchClient.executeQuery("posts/_search", (new ElasticSearchQueryBuilder(0, new FilterFields[]{FilterFields.carousel}, "true")).getQuery());
    }
}
