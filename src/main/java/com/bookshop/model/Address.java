package com.bookshop.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {
    private String street1;

    private String street2;

    private String city;

    private String state;

    private String zipCode;

    private String country;
}
