package com.bookshop.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {
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
        Order order = new Order();
        order.setOrderNumber("A1");
        order.setEmails(List.of("customer@domain.com"));
        order.setOrderItems(List.of(new OrderItem("New Item", 1), new OrderItem("Another Item", 5)));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(0, violations.size());
    }

    @Test
    void shouldRaise1ConstraintViolationForOrderWithOrderItemWithInvalidQuantity() {
        Order order = new Order();
        order.setOrderNumber("A1");
        order.setEmails(List.of("customer@domain.com"));
        order.setOrderItems(List.of(new OrderItem("New Item", 0)));

        assertThrows(NullPointerException.class, () -> new OrderItem("Invalid Item", null));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(1, violations.size());
        ConstraintViolation<Order> violation = violations.iterator().next();

        assertEquals("orderItems[0].quantity", violation.getPropertyPath().toString());
        assertEquals(Order.class, violation.getRootBean().getClass());
        assertEquals(OrderItem.class, violation.getLeafBean().getClass());
    }
}
