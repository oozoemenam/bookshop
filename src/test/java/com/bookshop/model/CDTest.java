package com.bookshop.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CDTest {
    private static ValidatorFactory vf;
    private static Validator validator;

    @BeforeAll
    static void init() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @AfterAll
    static void close() {
        if (vf != null) vf.close();
    }

    @Test
    void shouldNotRaiseConstraintViolation() {
        CD cd = new CD();
        cd.setTitle("New Title");
        cd.setPrice(BigDecimal.valueOf(9.99));
        cd.setTracks(Map.of(1, "Track 1"));

        Set<ConstraintViolation<CD>> violations = validator.validate(cd);
        assertEquals(0, violations.size());
    }

    @Test
    void shouldRaise3ConstraintViolationsTitlePriceTracks() {
        CD cd = new CD();

        Set<ConstraintViolation<CD>> violations = validator.validate(cd);
        assertEquals(3, violations.size());
    }

    @Test
    void shouldRaise1ConstraintViolationNegativePrice() {
        CD cd = new CD();
        cd.setTitle("New Title");
        cd.setPrice(BigDecimal.valueOf(-9.99));
        cd.setTracks(Map.of(1, "Track 1"));

        Set<ConstraintViolation<CD>> violations = validator.validate(cd);
        assertEquals(1, violations.size());
        ConstraintViolation<CD> violation = violations.iterator().next();

        assertEquals("must be greater than 0", violation.getMessage());
        assertEquals("{jakarta.validation.constraints.Positive.message}", violation.getMessageTemplate());
        assertEquals(BigDecimal.valueOf(-9.99), violation.getInvalidValue());
        assertEquals("price", violation.getPropertyPath().toString());
        assertEquals(CD.class, violation.getRootBeanClass());
        assertTrue(violation.getConstraintDescriptor().getAnnotation() instanceof jakarta.validation.constraints.Positive);
        assertEquals("New Title", violation.getRootBean().getTitle());
    }

    @Test
    void shouldNotRaiseConstraintViolationForPropertyValidation() {
        CD cd = new CD();
        cd.setTitle("New Title");

        Set<ConstraintViolation<CD>> violations = validator.validateProperty(cd, "title");
        assertEquals(0, violations.size());
    }

    @Test
    void shouldRaise1ConstraintViolationTitleForPropertyValidation() {
        CD cd = new CD();
        cd.setTitle("");

        Set<ConstraintViolation<CD>> violations = validator.validateProperty(cd, "title");
        assertEquals(1, violations.size());
        ConstraintViolation<CD> violation = violations.iterator().next();

        assertEquals("must not be blank", violation.getMessage());
        assertEquals("", violation.getInvalidValue());
        assertEquals("{jakarta.validation.constraints.NotBlank.message}", violation.getMessageTemplate());

    }

    @Test
    void shouldNotRaiseConstraintViolationForValueValidation() {
        Set<ConstraintViolation<CD>> violations = validator.validateValue(CD.class, "title", "New Title");
        assertEquals(0, violations.size());
    }

    @Test
    void shouldRaise1ConstraintViolationForValueValidation() {
        Set<ConstraintViolation<CD>> violations = validator.validateValue(CD.class, "title", "");
        assertEquals(1, violations.size());
    }

    @Test
    void shouldRaise1ConstraintViolationForMethodParameterValidation() throws NoSuchMethodException {
        CD cd = new CD();
        cd.setTitle("New Title");
        cd.setPrice(BigDecimal.valueOf(9.99));
        cd.setTracks(Map.of(1, "Track 1"));

        ExecutableValidator methodValidator = validator.forExecutables();
        Method method = CD.class.getMethod("calculatePrice", BigDecimal.class);
        Set<ConstraintViolation<CD>> violations = methodValidator.validateParameters(cd, method, new Object[]{new BigDecimal("1.2")});
        assertEquals(1, violations.size());
    }
}
