package com.bookshop.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Chapter {
    @NotBlank
    @NonNull
    private String title;
    private String description;
}
