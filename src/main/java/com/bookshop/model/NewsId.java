package com.bookshop.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class NewsId implements Serializable {
    private String title;
    private String language;
}
