package com.bookshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Size;

public class SizeValidatorForCharSequence implements ConstraintValidator<Size, CharSequence> {
    private int min;
    private int max;

    @Override
    public void initialize(Size parameters) {
        this.min = parameters.min();
        this.max = parameters.max();
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext ctx) {
        if (charSequence == null) {
            return true;
        } else {
            int length = charSequence.length();
            return length >= this.min && length <= this.max;
        }
    }
}
