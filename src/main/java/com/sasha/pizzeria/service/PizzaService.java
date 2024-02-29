package com.sasha.pizzeria.service;

import com.sasha.pizzeria.persistence.entity.PizzaEntity;
import com.sasha.pizzeria.persistence.repository.PizzaPagSortRepository;
import com.sasha.pizzeria.persistence.repository.PizzaRepository;
import com.sasha.pizzeria.service.dto.UpdatePizzaPriceDto;
import com.sasha.pizzeria.service.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PizzaService {
  private final PizzaRepository pizzaRepository;
  private final PizzaPagSortRepository pizzaPagSortRepository;

  @Autowired //se encarga de la inyeccion de dependencias de todos estos componentes
  public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
    this.pizzaRepository = pizzaRepository;
    this.pizzaPagSortRepository = pizzaPagSortRepository;
  }

  public Page<PizzaEntity> getAll(int page, int elements) {
    Pageable pageRequest = PageRequest.of(page, elements);
    return this.pizzaPagSortRepository.findAll(pageRequest);
  }

  public Page<PizzaEntity> getAvailable(int page, int elements, String sortBy, String sortDirection) {
    System.out.println(this.pizzaRepository.countByVeganTrue());

    Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
    Pageable pageRequest = PageRequest.of(page, elements, sort);

    return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
  }

  public PizzaEntity getByName(String name) {
    return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(() -> new RuntimeException("Couldn't find " + name));
  }

  public List<PizzaEntity> getWith(String ingredient) {
    return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
  }

  public List<PizzaEntity> getWithout(String ingredient) {
    return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
  }

  public List<PizzaEntity> getCheapest(double price) {
    BigDecimal maxPrice = BigDecimal.valueOf(price);
    List<PizzaEntity> pizzas = this.pizzaRepository.findByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(maxPrice);
    // Retorna solo los primeros 3 elementos si hay más de 3
    return pizzas.subList(0, Math.min(pizzas.size(), 3));
  }

  public PizzaEntity get(int idPizza){
    return this.pizzaRepository.findById(idPizza).orElse(null);
  }

  public PizzaEntity save(PizzaEntity pizza) {
    return this.pizzaRepository.save(pizza);
  }

  public void delete(int idPizza) {
    this.pizzaRepository.deleteById(idPizza);
  }

  @Transactional(noRollbackFor = EmailApiException.class)
  public void updatePrice(UpdatePizzaPriceDto dto) {
    this.pizzaRepository.updatePrice(dto);
    this.sendEmail();
  }

  private void sendEmail() {
    throw new EmailApiException();
  }
  public boolean exists (int idPizza) {
    return this.pizzaRepository.existsById(idPizza);
  }
}
