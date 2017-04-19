package com.konrad.examples.blog.posts.input.validators.impl;

import com.konrad.examples.blog.posts.filter.FilterFields;
import com.konrad.examples.blog.posts.input.validators.Validator;

public class SearchCategoryValidator implements Validator<String> {
    @Override
    public String validate(String s) {
        FilterFields.valueOf(s);
        return s;
    }
}
