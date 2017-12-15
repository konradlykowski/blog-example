package com.konrad.examples.blog;

import com.konrad.examples.blog.elasticsearch.query.ElasticSearchQueryBuilder;
import com.konrad.examples.blog.posts.filter.FilterFields;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ElasticSearchQueryBuilderTest {

    @Test
    public void getQueryNoMatchers() {
        ElasticSearchQueryBuilder elasticSearchQueryBuilder = new ElasticSearchQueryBuilder(2, null, null);
        Assert.assertEquals("{\"from\":2,\"size\":7,\"query\":{\"match_all\" : {}},\"sort\":{\"date\":{\"order\":\"desc\"}}}", elasticSearchQueryBuilder.getQuery());
    }

    @Test
    public void getQueryWithTwoMatchers() {
        ElasticSearchQueryBuilder elasticSearchQueryBuilder = new ElasticSearchQueryBuilder(2, (new FilterFields[]{FilterFields.tags, FilterFields.content, FilterFields.location}), "Zurich");
        Assert.assertEquals("{\"from\":2,\"size\":7,\"query\":{\"bool\": {\"should\": [{ \"match\": { \"tags\" : \"Zurich\"} },{ \"match\": { \"content\" : \"Zurich\"} },{ \"match\": { \"location\" : \"Zurich\"} }]}},\"sort\":{\"date\":{\"order\":\"desc\"}}}", elasticSearchQueryBuilder.getQuery());
    }
}