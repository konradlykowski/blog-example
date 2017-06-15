package com.konrad.examples.blog.comments.repository;

import com.konrad.examples.blog.comments.domain.Comment;
import com.konrad.examples.blog.elasticsearch.ElasticSearchClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class CommentsRepositoryTest {

    @Test
    public void addComment() {
        CommentsRepository commentsRepository = new CommentsRepository(new ElasticSearchClient(), new RestTemplate());
        Comment comment = new Comment();
        comment.name = "name";
        comment.ip = "ip.ip";
        commentsRepository.addComment(comment);
    }

    @Test
    public void getComments() {
        CommentsRepository commentsRepository = new CommentsRepository(new ElasticSearchClient(), new RestTemplate());
        commentsRepository.getCommentsByPostId("null");
    }

}