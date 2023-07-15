package com.bookshop.model;

import com.bookshop.validation.constraints.ChronologicalDates;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@ChronologicalDates
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Pattern(regexp = "[ABC][0-9]+")
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    @Valid
    private Customer customer;

    @PastOrPresent
    private LocalDate creationDate;

    private LocalDate paymentDate;

    @Future
    private LocalDate deliveryDate;

    @Valid
    private Address deliveryAddress;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    // @NotNull
    @NotEmpty
    private List<@Valid OrderItem> orderItems;

    private BigDecimal totalAmount;

    @ElementCollection
    @NotEmpty
    private List<@Email @NotBlank String> emails;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
