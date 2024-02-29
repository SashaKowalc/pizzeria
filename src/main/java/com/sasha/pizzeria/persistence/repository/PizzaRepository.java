package com.sasha.pizzeria.persistence.repository;

import com.sasha.pizzeria.persistence.entity.PizzaEntity;
import com.sasha.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
  List<PizzaEntity> findAllByAvailableTrueOrderByPrice();
  Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);
  List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);
  List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);
  int countByVeganTrue();
  List<PizzaEntity> findByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(BigDecimal maxPrice);


  @Query(value = "UPDATE pizza " +
      "SET price = :#{#newPizzaPrice.newPrice} " + //con esta anotacion :#{#test} lo que hara es individualizar los atributos que tiene y utilizarlos como parametros dentro de nuestra consulta
      "WHERE id_pizza = :#{#newPizzaPrice.pizzaId}", nativeQuery = true)
  @Modifying
  void updatePrice(@Param("newPizzaPrice") UpdatePizzaPriceDto newPizzaPrice);

}
