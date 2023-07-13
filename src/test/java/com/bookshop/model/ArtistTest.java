package com.bookshop.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ArtistTest {
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
        Artist artist = new Artist();
        artist.setFirstName("John");
        artist.setLastName("Paul");

        Set<ConstraintViolation<Artist>> violations = validator.validate(artist);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldRaiseConstraintViolationForNullFirstName() {
        Artist artist = new Artist();
        artist.setLastName("Paul");

        Set<ConstraintViolation<Artist>> violations = validator.validate(artist);
        assertEquals(1, violations.size());
    }

    @Test
    void shouldRaiseConstraintViolationForEmptyFirstName() {
        Artist artist = new Artist();
        artist.setFirstName("");
        artist.setLastName("Paul");

        Set<ConstraintViolation<Artist>> violations = validator.validate(artist);
        assertEquals(1, violations.size());
    }

    @Test
    void shouldRaiseConstraintViolationForInvalidEmail() {
        Artist artist = new Artist();
        artist.setFirstName("John");
        artist.setLastName("Paul");
        artist.setEmail("invalid");

        Set<ConstraintViolation<Artist>> violations = validator.validate(artist);
        assertEquals(1, violations.size());
        ConstraintViolation<Artist> violation = violations.iterator().next();
        assertEquals("must be a well-formed email address", violation.getMessage());
        assertEquals("invalid", violation.getInvalidValue());
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    void shouldRaise2ConstraintViolationsForInvalidEmailAndInvalidDateOfBirth() {
        Artist artist = new Artist();
        artist.setFirstName("John");
        artist.setLastName("Paul");
        artist.setEmail("invalid");
        artist.setDateOfBirth(LocalDate.of(2200, 12, 31));

        Set<ConstraintViolation<Artist>> violations = validator.validate(artist);
        assertEquals(2, violations.size());
    }
}
