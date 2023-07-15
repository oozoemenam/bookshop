package com.bookshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @NonNull
    private String firstName;

    @NotEmpty
    @NonNull
    private String lastName;

    @Email
    @NonNull
    private String email;

    @Column(length = 15)
    private String phoneNumber;

    @ElementCollection
    private List<Address> addresses;

    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    // Same as @NotBlank
    @PrePersist
    @PreUpdate
    private void validate() {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("Invalid first name");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Invalid last name");
        }
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculateAge() {
        if (dateOfBirth == null) {
            age = null;
            return;
        }
        age = Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
