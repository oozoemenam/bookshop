package com.bookshop.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Address {
    @NotBlank
    @NonNull
    private String street1;

    private String street2;

    @NotBlank
    @NonNull
    private String city;

    private String state;

    private String zipCode;

    @NotBlank
    @NonNull
    private String country;
}
