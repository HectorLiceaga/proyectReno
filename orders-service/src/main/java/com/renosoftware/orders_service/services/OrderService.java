package com.renosoftware.orders_service.services;

import com.renosoftware.orders_service.model.dtos.BaseResponse;
import com.renosoftware.orders_service.model.dtos.request.OrderItemRequest;
import com.renosoftware.orders_service.model.dtos.request.OrderRequest;
import com.renosoftware.orders_service.model.dtos.response.OrderResponse;
import com.renosoftware.orders_service.model.dtos.response.OrderItemResponse;
import com.renosoftware.orders_service.model.entities.Order;
import com.renosoftware.orders_service.model.entities.OrderItem;
import com.renosoftware.orders_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderResponse placeOrder(OrderRequest orderRequest) {

        BaseResponse result = this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if(result != null && !result.hasErrors()) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                    .map(this::mapOrderItemRequestToOrderItem)
                    .toList();
            orderItems.forEach(orderItem -> orderItem.setOrder(order));
            order.setOrderItems(orderItems);
            log.info("Added order: {}", order);
            return this.mapToOrderResponse(orderRepository.save(order));
        } else {
            throw new IllegalArgumentException("Some of the products are not in stock.");
        }
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();

        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderNumber(),
                order.getOrderItems().stream().map(this::mapToOrderItemResponse).toList());
    }

    private OrderItem mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest) {
        return OrderItem.builder()
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .build();
    }

    private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {
        System.out.println(orderItem);
        log.info(orderItem.toString());
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .sku(orderItem.getSku())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
