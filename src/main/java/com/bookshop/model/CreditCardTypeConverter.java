package com.bookshop.model;

import com.bookshop.enums.CreditCardType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static com.bookshop.enums.CreditCardType.*;

@Converter
public class CreditCardTypeConverter implements AttributeConverter<CreditCardType, Character> {

    @Override
    public Character convertToDatabaseColumn(CreditCardType creditCardType) {
        return switch (creditCardType) {
            case VISA -> 'V';
            case AMERICAN_EXPRESS -> 'A';
            case MASTER_CARD -> 'M';
            default -> throw new IllegalArgumentException("Unknown " + creditCardType);
        };
    }

    @Override
    public CreditCardType convertToEntityAttribute(Character dbData) {
        return switch (dbData) {
            case 'V' -> VISA;
            case 'A' -> AMERICAN_EXPRESS;
            case 'M' -> MASTER_CARD;
            default -> throw new IllegalArgumentException("Unknown " + dbData);
        };
    }
}
