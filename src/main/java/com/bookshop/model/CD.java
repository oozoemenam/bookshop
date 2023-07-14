package com.bookshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class CD extends Item {

    private Float totalDuration;

    private String genre;

    @ManyToMany(mappedBy = "appearsOnCDs")
    @ToString.Exclude
    private Set<Artist> createdByArtists = new HashSet<>();

    @Lob
    private byte[] cover;

    @ElementCollection
    @CollectionTable(name = "track")
    @MapKeyColumn(name = "position")
    @Column(name = "title")
    @NotEmpty
    @Size(max = 12)
    private Map<@Positive Integer, @NotBlank String> tracks = new HashMap<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CD cd = (CD) o;
        return getId() != null && Objects.equals(getId(), cd.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
