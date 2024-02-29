package com.sasha.pizzeria.persistence.entity;

import com.sasha.pizzeria.persistence.audit.AuditPizzaListener;
import com.sasha.pizzeria.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "pizza")
@EntityListeners({AuditingEntityListener.class, AuditPizzaListener.class})
@Getter
@Setter
@NoArgsConstructor
public class PizzaEntity extends AuditableEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_pizza", nullable = false)
  private Integer idPizza;

  @Column(nullable = false, length = 30, unique = true)
  private String name;

  @Column(nullable = false, length = 150)
  private String description;

  @Column(nullable = false, columnDefinition = "Decimal(5,2)")
  private BigDecimal price;

  @Column(columnDefinition = "BIT")
  private Boolean vegetarian;

  @Column(columnDefinition = "BIT")
  private Boolean vegan;

  @Column(columnDefinition = "BIT", nullable = false)
  private Boolean available;

  @Override
  public String toString() {
    return "PizzaEntity{" +
        "idPizza=" + idPizza +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", vegetarian=" + vegetarian +
        ", vegan=" + vegan +
        ", available=" + available +
        '}';
  }
}
