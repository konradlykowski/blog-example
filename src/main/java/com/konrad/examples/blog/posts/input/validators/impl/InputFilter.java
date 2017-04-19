package com.konrad.examples.blog.posts.input.validators.impl;

import com.konrad.examples.blog.posts.input.validators.Validator;
import org.springframework.web.util.HtmlUtils;

public class InputFilter implements Validator<String> {

    public String validate(String filter) {
        return HtmlUtils.htmlEscape(filter);
    }
}
