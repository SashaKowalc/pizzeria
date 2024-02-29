package com.sasha.pizzeria.persistence.repository;

import com.sasha.pizzeria.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends ListCrudRepository<CustomerEntity, String> {
  //En @Query escribimos la consulta en JPQL
  //c -> resultado
  //CostumerEntity -> Ya no se llama por el nombre de la tabla en la DB, sino por el nombre de su entity (costumer)
  @Query(value = "SELECT c FROM CustomerEntity c WHERE c.phoneNumber = :phone")
  CustomerEntity findByPhone(@Param("phone") String phone);
}
