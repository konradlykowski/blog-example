package com.konrad.examples.blog.posts.input.validators.impl;

import com.konrad.examples.blog.posts.input.validators.Validator;

public class LengthValidator implements Validator<String> {
    @Override
    public String validate(String input) {
        if (input.length() > 15) {
            throw new IllegalArgumentException("LengthValidator");
        }
        return input;
    }
}
