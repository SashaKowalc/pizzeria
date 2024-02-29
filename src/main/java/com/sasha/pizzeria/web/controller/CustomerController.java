package com.sasha.pizzeria.web.controller;

import com.sasha.pizzeria.persistence.entity.CustomerEntity;
import com.sasha.pizzeria.persistence.entity.OrderEntity;
import com.sasha.pizzeria.service.CustomerService;
import com.sasha.pizzeria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
  private final CustomerService customerService;
  private final OrderService orderService;

  @Autowired
  public CustomerController(CustomerService customerService, OrderService orderService) {
    this.customerService = customerService;
    this.orderService = orderService;
  }

  @GetMapping("/phone/{phone}")
  public ResponseEntity<CustomerEntity> getByPhone(@PathVariable String phone) {
    return ResponseEntity.ok(this.customerService.findByPhone(phone));
  }

  @GetMapping("/customer/{idCustomer}")
  public ResponseEntity<List<OrderEntity>> getCustomerOrders(@PathVariable String idCustomer){
    return ResponseEntity.ok(this.orderService.getCustomerOrders(idCustomer));
  }


}
