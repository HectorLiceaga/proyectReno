package com.renosoftware.orders_service.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="order_items")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private Double price;
    private Long quantity;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}
