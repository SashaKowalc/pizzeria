package com.sasha.pizzeria.service.dto;

import lombok.Data;

@Data //sirve para crear automaticamente todos los getters, setters, contrusctores obligatorios, etc.
public class UpdatePizzaPriceDto {
  private int pizzaId;
  private double newPrice;
}
