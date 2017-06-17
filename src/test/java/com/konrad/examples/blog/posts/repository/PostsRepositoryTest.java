package com.konrad.examples.blog.posts.repository;

import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import com.konrad.examples.blog.posts.input.validators.DefaultInputValidator;
import com.konrad.examples.blog.posts.input.validators.SearchByInputValidator;
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
        System.out.println(new PostsRepository(elasticSearchClient, restTemplate).getPostById("geometryczna-suknia-nowa-11.html"));
        Mockito.verify(elasticSearchClient).executeGet("posts/post/geometryczna-suknia-nowa-11.html");
    }


}