package com.bookshop.model;

import com.bookshop.validation.constraints.ZipCode;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    @NonNull
    private String street1;

    private String street2;

    @NotEmpty
    @NonNull
    private String city;

    private String state;

    @ZipCode
    @NonNull
    private String zipCode;

    @NotEmpty
    @NonNull
    private String country;
}
