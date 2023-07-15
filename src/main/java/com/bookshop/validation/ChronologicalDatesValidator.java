package com.bookshop.validation;

import com.bookshop.model.Order;
import com.bookshop.validation.constraints.ChronologicalDates;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChronologicalDatesValidator implements ConstraintValidator<ChronologicalDates, Order> {

    @Override
    public boolean isValid(Order order, ConstraintValidatorContext ctx) {
        if (order.getCreationDate() == null || order.getDeliveryDate() == null || order.getPaymentDate() == null) {
            return true;
        }
        return order.getCreationDate().isBefore(order.getPaymentDate()) &&
                order.getPaymentDate().isBefore(order.getDeliveryDate());
    }
}
