package com.konrad.examples.blog.posts.input.validators;

import com.konrad.examples.blog.comments.domain.Comment;
import com.konrad.examples.blog.posts.input.validators.impl.InputFilter;
import com.konrad.examples.blog.posts.input.validators.impl.JsonEscaperFilter;
import com.konrad.examples.blog.posts.input.validators.impl.LengthValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class CommentsInputValidator {

    public Validator[] validators = new Validator[3];

    public CommentsInputValidator() {

    }

    public Comment validate(Comment comment) {
        Set<ConstraintViolation<Comment>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(comment);
        if (constraintViolations.size() != 0) {
            throw new IllegalArgumentException("Comment not valid.");
        }
        DefaultInputValidator defaultInputValidator = new DefaultInputValidator();
        comment.setName(defaultInputValidator.validate(comment.getName()));
        comment.setText(defaultInputValidator.validate(comment.getText()));
        return comment;
    }
}
