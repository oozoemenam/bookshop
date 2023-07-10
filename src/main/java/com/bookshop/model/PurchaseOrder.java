package com.bookshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class PurchaseOrder {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "join_order_line",
//            joinColumns = @JoinColumn(name = "order_fk"),
//            inverseJoinColumns = @JoinColumn(name = "order_line_fk")
//    )
    @JoinColumn(name = "order_fk")
    private List<OrderLine> orderLines;
}
