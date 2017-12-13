package com.konrad.examples.blog.posts.input.validators.impl;

import com.konrad.examples.blog.posts.input.validators.Validator;

public class LengthValidator implements Validator<String> {

    private final int len;

    public LengthValidator() {
        len = 150;
    }

    public LengthValidator(int len) {
        this.len = len;
    }

    @Override
    public String validate(String input) {
        if (input.length() > len) {
            throw new IllegalArgumentException("LengthValidator");
        }
        return input;
    }
}
