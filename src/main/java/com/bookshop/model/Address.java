package com.bookshop.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @Size(max = 5)
    private String zipCode;

    @NotBlank
    @NonNull
    private String country;
}
