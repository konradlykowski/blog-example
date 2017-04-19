package com.konrad.examples.blog.posts.input.validators;

public interface Validator<T> {
    T validate(T t);
}
