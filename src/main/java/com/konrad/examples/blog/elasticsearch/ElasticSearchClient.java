package com.konrad.examples.blog.elasticsearch;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

public class ElasticSearchClient {

    public final static String ELASTIC_SEARCH_URL = "https://vpc-elasticigla-j4uv6iwavksdnr5rvrfxs7plei.eu-central-1.es.amazonaws.com/";
    //public final static String ELASTIC_SEARCH_URL = "http://localhost:9200/";
    private RestTemplate restTemplate;
    Logger log = Logger.getLogger(this.getClass().getName());

    public ElasticSearchClient executeOn(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }

    public String executeQuery(String url, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity(body, headers);
        log.info(ELASTIC_SEARCH_URL + url);
        log.info(entity.getBody());
        return restTemplate.postForEntity(ELASTIC_SEARCH_URL + url, entity, String.class).getBody();
    }

    public String executeGet(String url) {
        return restTemplate.getForEntity(ELASTIC_SEARCH_URL + url, String.class).getBody();
    }

}
