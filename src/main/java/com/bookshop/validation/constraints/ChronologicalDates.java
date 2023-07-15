package com.bookshop.validation.constraints;

import com.bookshop.validation.ChronologicalDatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChronologicalDatesValidator.class)
public @interface ChronologicalDates {

    String message() default "Dates must be chronological";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
