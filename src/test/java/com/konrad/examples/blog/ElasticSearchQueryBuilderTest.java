package com.konrad.examples.blog;

import com.konrad.examples.blog.elasticsearch.query.ElasticSearchQueryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ElasticSearchQueryBuilderTest {

    @Test
    public void getQueryNoMatchers() {
        ElasticSearchQueryBuilder elasticSearchQueryBuilder = new ElasticSearchQueryBuilder(2, null, null);
        Assert.assertEquals("{\n" +
                "\"from\" : 2, \"size\" : 7,    \"query\": {\n" +
                "\"match_all\" : {}},\n" +
                "    \"sort\": { \"date\": { \"order\": \"desc\" }}\n" +
                "} ", elasticSearchQueryBuilder.getQuery());
    }

    @Test
    public void getQueryWithTwoMatchers() {
        ElasticSearchQueryBuilder elasticSearchQueryBuilder = new ElasticSearchQueryBuilder(2, (new String[]{"tags", "content", "location"}), "Zurich");
        Assert.assertEquals("{\n" +
                "\"from\" : 2, \"size\" : 7,    \"query\": {\n" +
                "\"bool\": {\"should\": [{ \"match\": { \"tags\" : \"Zurich\"} },{ \"match\": { \"content\" : \"Zurich\"} },{ \"match\": { \"location\" : \"Zurich\"} }]}},\n" +
                "    \"sort\": { \"date\": { \"order\": \"desc\" }}\n" +
                "} ", elasticSearchQueryBuilder.getQuery());
    }
}