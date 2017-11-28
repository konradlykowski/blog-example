package com.konrad.examples.blog;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;

public class RequestHandlerController implements RequestHandler<Map<String, String>, String> {

    private final RestTemplate restTemplate;
    private static Logger LOGGER = LoggerFactory.getLogger(RequestHandlerController.class);


    public RequestHandlerController() {
        restTemplate = new RestTemplate();
    }

    public RequestHandlerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String handleRequest(Map<String, String> stringMap, Context context) {
        LOGGER.info(stringMap.toString());
        final String action = HtmlUtils.htmlEscape(stringMap.get("action"));
        switch (action) {
            case "getComments": {
                return (new CommentsReader(restTemplate)).getComments(HtmlUtils.htmlEscape(stringMap.get("postId"), "UTF-8"));
            }
            case "getAllPosts": {
                return (new PostsReader(restTemplate)).getAllPosts(Integer.valueOf(HtmlUtils.htmlEscape(stringMap.get("from"), "UTF-8")));
            }
            case "getPostsByCategory": {
                return (new PostsReader(restTemplate)).getPostsByCategory(Integer.valueOf(HtmlUtils.htmlEscape(stringMap.get("from"), "UTF-8")),
                        HtmlUtils.htmlEscape(stringMap.get("category"), "UTF-8"));
            }
            case "getPostsByTag": {
                return (new PostsReader(restTemplate)).getPostsByTag(Integer.valueOf(HtmlUtils.htmlEscape(stringMap.get("from"), "UTF-8")),
                        HtmlUtils.htmlEscape(stringMap.get("tag"), "UTF-8"));
            }
            case "getPostsByLocation": {
                return (new PostsReader(restTemplate)).getPostsByLocation(Integer.valueOf(HtmlUtils.htmlEscape(stringMap.get("from"), "UTF-8")),
                        HtmlUtils.htmlEscape(stringMap.get("location"), "UTF-8"));
            }
            case "getPostsByQuery": {
                return (new PostsReader(restTemplate)).getPostsByQuery(Integer.valueOf(HtmlUtils.htmlEscape(stringMap.get("from"), "UTF-8")),
                        HtmlUtils.htmlEscape(stringMap.get("query"), "UTF-8"));
            }
            default: {
                throw new RuntimeException("Bad action");
            }
        }
    }


}
