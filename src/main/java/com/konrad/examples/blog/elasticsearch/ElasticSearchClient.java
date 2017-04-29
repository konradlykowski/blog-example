package com.konrad.examples.blog.elasticsearch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class ElasticSearchClient {

    public final static String ELASTIC_SEARCH_URL = "https://vpc-iglawpodrozy-6xrukndwc6cyellfdhpfu4y6ma.eu-central-1.es.amazonaws.com/";
    private RestTemplate restTemplate;

    public ElasticSearchClient executeOn(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }

    public String executeQuery(String url, String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity(query, headers);
        return restTemplate.postForEntity(ELASTIC_SEARCH_URL + url, entity, String.class).getBody();
    }

    public String executeGet(String url) {
        return restTemplate.getForEntity(ELASTIC_SEARCH_URL + url, String.class).getBody();
    }

}
