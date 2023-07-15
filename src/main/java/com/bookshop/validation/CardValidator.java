//package com.bookshop.validation;
//
//import com.bookshop.model.CreditCard;
//import jakarta.validation.constraints.AssertTrue;
//import jakarta.validation.constraints.Future;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//import java.time.LocalDate;
//
//public class CardValidator {
//    private ValidationAlgorithm algorithm;
//
//    public CardValidator(@NotNull ValidationAlgorithm algorithm) {
//        this.algorithm = algorithm;
//    }
//
//    @AssertTrue
//    public Boolean validate(@NotNull CreditCard creditCard) {
//        return algorithm.validate(creditCard.getNumber(), creditCard.getControlNumber());
//    }
//
//    @AssertTrue
//    public Boolean validate(
//            @NotBlank String number,
//            @Future LocalDate expiryDate,
//            @NotNull Integer controlNumber
//    ) {
//        return algorithm.validate(number, controlNumber);
//    }
//}
