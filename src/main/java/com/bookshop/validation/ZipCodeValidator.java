package com.bookshop.validation;

import com.bookshop.validation.constraints.ZipCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipCodeValidator implements ConstraintValidator<ZipCode, String> {
    private static final Pattern zipPattern = Pattern.compile("\\d{5}(-\\d{5})?");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        Matcher m = zipPattern.matcher(value);
        return m.matches();
    }
}
