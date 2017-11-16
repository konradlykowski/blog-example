package com.konrad.examples.blog;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;

public class RequestHandlerController implements RequestHandler<Map<String, String>, String> {

    private final RestTemplate restTemplate;

    public RequestHandlerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String handleRequest(Map<String, String> stringMap, Context context) {
        final String action = HtmlUtils.htmlEscape(stringMap.get("action"));
        switch (action) {
            case "getComments": {
                return (new CommentsReader(restTemplate)).getComments(HtmlUtils.htmlEscape(stringMap.get("postId")));
            }
            case "getAllPosts": {
                return (new PostsReader(restTemplate)).getAllPosts(Integer.valueOf(HtmlUtils.htmlEscape(stringMap.get("from"))));
            }
            case "getPostsByCategory": {
                return (new PostsReader(restTemplate)).getPostsByCategory(Integer.valueOf(HtmlUtils.htmlEscape(stringMap.get("from"))),
                        HtmlUtils.htmlEscape(stringMap.get("category")));
            }
            case "getPostsByTag": {
                return (new PostsReader(restTemplate)).getPostsByTag(Integer.valueOf(HtmlUtils.htmlEscape(stringMap.get("from"))),
                        HtmlUtils.htmlEscape(stringMap.get("tag")));
            }
            case "getPostsByQuery": {
                return (new PostsReader(restTemplate)).getPostsByQuery(Integer.valueOf(HtmlUtils.htmlEscape(stringMap.get("from"))),
                        HtmlUtils.htmlEscape(stringMap.get("query")));
            }
            default: {
                throw new RuntimeException("Bad action");
            }
        }
    }


}
