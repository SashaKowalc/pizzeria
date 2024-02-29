package com.sasha.pizzeria.service;

import com.sasha.pizzeria.persistence.entity.CustomerEntity;
import com.sasha.pizzeria.persistence.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public CustomerEntity findByPhone(String phone) {
    return this.customerRepository.findByPhone(phone);
  }


}
