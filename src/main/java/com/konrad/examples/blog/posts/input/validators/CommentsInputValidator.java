package com.konrad.examples.blog.posts.input.validators;

import com.konrad.examples.blog.posts.input.validators.impl.InputFilter;
import com.konrad.examples.blog.posts.input.validators.impl.JsonEscaperFilter;
import com.konrad.examples.blog.posts.input.validators.impl.LengthValidator;

public class CommentsInputValidator {

    public Validator[] validators = new Validator[3];

    public CommentsInputValidator() {
        validators[0] = new LengthValidator(1500);
        validators[1] = new InputFilter();
        validators[2] = new JsonEscaperFilter();
    }

    public String validate(String input) {
        for (Validator<String> validator : validators) {
            input = validator.validate(input);
        }
        return input;
    }
}
