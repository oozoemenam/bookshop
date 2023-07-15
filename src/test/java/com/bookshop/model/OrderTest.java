package com.bookshop.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
//        System.out.println("!!!!!!!!!!!!!!!!!!");
//        System.out.println(violations.iterator().next().getPropertyPath());
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

    @Test
    void shouldNotRaiseConstraintViolationForChronologicalDates() {
        Order order = new Order();
        order.setOrderNumber("A1");
        order.setEmails(List.of("customer@domain.com"));
        order.setOrderItems(List.of(new OrderItem("New Item", 1), new OrderItem("Another Item", 5)));
        order.setCreationDate(LocalDate.now());
        order.setPaymentDate(LocalDate.now().plusDays(1));
        order.setDeliveryDate(LocalDate.now().plusDays(2));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(0, violations.size());
    }

    @Test
    void shouldRaise1ConstraintViolationForChronologicalDates() {
        Order order = new Order();
        order.setOrderNumber("A1");
        order.setEmails(List.of("customer@domain.com"));
        order.setOrderItems(List.of(new OrderItem("New Item", 1), new OrderItem("Another Item", 5)));
        order.setCreationDate(LocalDate.now());
        order.setPaymentDate(LocalDate.now().plusDays(-1));
        order.setDeliveryDate(LocalDate.now().plusDays(2));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(1, violations.size());
    }

    @Test
    void shouldNotRaiseConstraintViolationForEntireObjectGraph() {
        Order order = new Order();
        order.setOrderNumber("A1");
        order.setEmails(List.of("customer@domain.com"));
        order.setOrderItems(List.of(new OrderItem("New Item", 1), new OrderItem("Another Item", 5)));
        order.setCreationDate(LocalDate.of(2023, 7, 14));
        order.setPaymentDate(LocalDate.of(2023, 7, 15));
        order.setDeliveryDate(LocalDate.of(2023, 7, 16));
        order.setDeliveryAddress(new Address("Via Mario Borsa 10", "Milano", "20050", "Italy"));
        order.setCustomer(new Customer("Michael", "Jordan", "mj@domain.com"));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(0, violations.size());
    }

    @Test
    void shouldRaise2ConstraintViolationsForInvalidCustomerFirstNameAndInvalidCustomerEmail() {
        Order order = new Order();
        order.setOrderNumber("A1");
        order.setEmails(List.of("customer@domain.com"));
        order.setOrderItems(List.of(new OrderItem("New Item", 1), new OrderItem("Another Item", 5)));
        order.setCreationDate(LocalDate.now());
        order.setPaymentDate(LocalDate.now().plusDays(1));
        order.setDeliveryDate(LocalDate.now().plusDays(2));
        order.setDeliveryAddress(new Address("Via Mario Borsa 10", "Milano", "20050", "Italy"));
        order.setCustomer(new Customer("", "Jordan", "invalid"));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertEquals(2, violations.size());
    }
}
