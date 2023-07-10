package com.bookshop.model;

import com.bookshop.enums.CreditCardType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CreditCard {
    @Id
    private String number;

    private String expiryDate;

    private Integer controlNumber;

    // @Enumerated(EnumType.STRING)
    @Convert(converter = CreditCardTypeConverter.class)
    private CreditCardType creditCardType;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CreditCard that = (CreditCard) o;
        return getNumber() != null && Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
