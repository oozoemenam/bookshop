package com.bookshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    protected String title;

    @Column(length = 500)
    protected String description;

    protected Float price;
}
