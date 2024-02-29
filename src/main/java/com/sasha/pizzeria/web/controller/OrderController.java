package com.sasha.pizzeria.web.controller;

import com.sasha.pizzeria.persistence.entity.OrderEntity;
import com.sasha.pizzeria.persistence.projection.OrderSummary;
import com.sasha.pizzeria.service.OrderService;
import com.sasha.pizzeria.service.dto.RandomOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public ResponseEntity<List<OrderEntity>> getAll(){
    return ResponseEntity.ok(this.orderService.getAll());
  }

  @GetMapping("/today")
  public ResponseEntity<List<OrderEntity>> getTodayOrders(){
    return ResponseEntity.ok(this.orderService.getTodayOrders());
  }

  @GetMapping("/outside")
  public ResponseEntity<List<OrderEntity>> getOutsideOrders(){
    return ResponseEntity.ok(this.orderService.getOutsideOrders());
  }

  @GetMapping("/customer/{idCustomer}")
  public ResponseEntity<List<OrderEntity>> getCustomerOrders(@PathVariable String idCustomer){
    return ResponseEntity.ok(this.orderService.getCustomerOrders(idCustomer));
  }

  @GetMapping("/summary/{orderId}")
  public ResponseEntity<OrderSummary> getSummary(@PathVariable int orderId) {
    return ResponseEntity.ok(this.orderService.getSummary(orderId));
  }

  @PostMapping("/random")
  public ResponseEntity<Boolean> randomOrder(@RequestBody RandomOrderDto dto) {
    return ResponseEntity.ok(this.orderService.saveRandomOrder(dto));
  }
}
