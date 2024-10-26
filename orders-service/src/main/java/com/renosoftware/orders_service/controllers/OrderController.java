package com.renosoftware.orders_service.controllers;

import com.renosoftware.orders_service.model.dtos.request.OrderRequest;
import com.renosoftware.orders_service.model.dtos.response.OrderResponse;
import com.renosoftware.orders_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println(orderRequest);
        return ResponseEntity.created(null).body(this.orderService.placeOrder(orderRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        return this.orderService.getAllOrders();
    }
}
