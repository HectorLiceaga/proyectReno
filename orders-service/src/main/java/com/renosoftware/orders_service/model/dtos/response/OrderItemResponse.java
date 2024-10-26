package com.renosoftware.orders_service.model.dtos.response;

import com.renosoftware.orders_service.model.entities.Order;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {
    private Long id;
    private String sku;
    private Double price;
    private Long quantity;
}
