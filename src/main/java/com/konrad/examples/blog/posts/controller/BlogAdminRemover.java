package com.konrad.examples.blog.posts.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class BlogAdminRemover implements RequestHandler<Map<String, String>, String> {
    private static Logger LOG = Logger.getLogger(BlogAdminRemover.class.getName());
    private static HttpHeaders headers = new HttpHeaders();

    static {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }


    public String handleRequest(Map<String, String> input, Context context) {
        RestTemplate restTemplate = new RestTemplate();
        LOG.info(input.get("url"));
        restTemplate.delete(input.get("url"));
        return "Removed.";
    }


}