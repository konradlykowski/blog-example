package com.konrad.examples.blog.posts.repository;

import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class PostsRepositoryTest {

    @Mock
    ElasticSearchClient elasticSearchClient;

    @Mock
    RestTemplate restTemplate;

    @Test
    public void getPostsUsesClient() {
        new PostsRepository(elasticSearchClient, restTemplate).getAllPosts(0);
        Mockito.verify(elasticSearchClient).executeQuery(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void getPostById() {
        new PostsRepository(elasticSearchClient, restTemplate).getPostById("");
        Mockito.verify(elasticSearchClient).executeGet("");
    }
}