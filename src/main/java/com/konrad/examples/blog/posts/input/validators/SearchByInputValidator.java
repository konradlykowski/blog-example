package com.konrad.examples.blog.posts.input.validators;

import com.konrad.examples.blog.posts.input.validators.impl.InputFilter;
import com.konrad.examples.blog.posts.input.validators.impl.JsonEscaperFilter;
import com.konrad.examples.blog.posts.input.validators.impl.LengthValidator;
import com.konrad.examples.blog.posts.input.validators.impl.SearchCategoryValidator;

public class SearchByInputValidator {

    public Validator[] validators = new Validator[4];

    public SearchByInputValidator() {
        validators[0] = new LengthValidator();
        validators[1] = new SearchCategoryValidator();
        validators[2] = new InputFilter();
        validators[3] = new JsonEscaperFilter();
    }

    public String validate(String input) {
        for (Validator<String> validator : validators) {
            input = validator.validate(input);
        }
        return input;
    }
}
