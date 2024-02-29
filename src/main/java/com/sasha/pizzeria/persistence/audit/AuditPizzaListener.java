package com.sasha.pizzeria.persistence.audit;

import com.sasha.pizzeria.persistence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;

public class AuditPizzaListener {

  private PizzaEntity currentValue;

  @PostLoad //Se va a ejecutar antes de hacer el metodo. //Solo se ejecuta despues de cargar la informacion en un select 
  public void postLoad(PizzaEntity entity) {
    System.out.println("POST LOAD " + entity);
    //this.currentValue = entity; POR COMO SE COMPORTA LA MEMORIA DE JAVA, ESTO NO SE PUEDE HACER YA QUE ESTARIAMOS SOBRECARGANDO
    // ESTE METODO EN MEMORIA Y NO ESTARIAMOS VIENDO EL CAMBIO ENTRE UN ENTITY Y EL OTRO
    this.currentValue = SerializationUtils.clone(entity);
  }

  @PostPersist //debe responder a un metodo que no devuelva nada
  @PostUpdate
  public void onPostPersist(PizzaEntity entity) {
    System.out.println("POST PRESIST UPDATE " + entity);
    System.out.println("OLD VALUE: " + this.currentValue.toString());
    System.out.println("NEW VALUE: " + entity.toString());
  }

  @PreRemove //Este metodo se ejecutara antes de realizar el proceso de eliminacion en la DB
  public void onPreDelete(PizzaEntity entity) {
    System.out.println(entity.toString());
  }

}
