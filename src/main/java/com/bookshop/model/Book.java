package com.bookshop.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NamedQuery(name = "findAllBooks", query = "SELECT b FROM Book b")
public class Book extends Item {
    @Column(unique = true, nullable = false)
    private String isbn;

    private String publisher;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tag")
    @Column(name = "name")
    private List<@NotBlank String> tags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "book_chapters",
            joinColumns = {@JoinColumn(name = "book_id")}
    )
    @MapKeyColumn(name = "position")
    // @Column(name = "chapter")
    private Map<Integer, @Valid Chapter> chapters = new HashMap<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_authors",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    @ToString.Exclude
    private Set<Author> authors = new HashSet<>();

    @Positive
    private Integer numOfPages;

    private Boolean illustrations;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Book book = (Book) o;
        return getId() != null && Objects.equals(getId(), book.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
