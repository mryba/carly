package org.carly.core.shared.security.validation;

import org.carly.core.usermanagement.model.UserRest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatcher, Object> {

    @Override
    public void initialize(PasswordMatcher constraint) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRest user = (UserRest) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}