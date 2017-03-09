package com.konrad.examples.blog.posts.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.logging.Logger;


public class BlogAdminAdder implements RequestHandler<Map<String, String>, String> {
    private static Logger LOG = Logger.getLogger(BlogAdminAdder.class.getName());
    private static HttpHeaders headers = new HttpHeaders();

    static {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    public String handleRequest(Map<String, String> input, Context context) {
        RestTemplate restTemplate = new RestTemplate();
        for (String inputKey : input.keySet()) {
            if ("url".equals(inputKey)) {
                LOG.info(input.get("url"));
                continue;
            }
            LOG.info(input.get("url") + "/" + inputKey);
            restTemplate.put(input.get("url") + "/" + inputKey, new HttpEntity(input.get(inputKey), headers));
        }
        return "Finished";
    }


}