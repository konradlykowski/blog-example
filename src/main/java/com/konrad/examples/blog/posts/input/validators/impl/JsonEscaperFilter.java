package com.konrad.examples.blog.posts.input.validators.impl;

import com.konrad.examples.blog.posts.input.validators.Validator;

import java.text.StringCharacterIterator;

public class JsonEscaperFilter implements Validator<String> {
    @Override
    public String validate(String s) {
        final StringBuilder result = new StringBuilder();
        StringCharacterIterator iterator = new StringCharacterIterator(s);
        char character = iterator.current();
        while (character != StringCharacterIterator.DONE) {
            if (character == '\"') {
                result.append("\\\"");
            } else if (character == '\\') {
                result.append("\\\\");
            } else if (character == '/') {
                result.append("\\/");
            } else if (character == '\b') {
                result.append("\\b");
            } else if (character == '\f') {
                result.append("\\f");
            } else if (character == '\n') {
                result.append("\\n");
            } else if (character == '\r') {
                result.append("\\r");
            } else if (character == '\t') {
                result.append("\\t");
            } else {
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }
}
