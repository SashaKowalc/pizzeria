package com.sasha.pizzeria.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {

  private String username;
  private String role;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    UserRoleId that = (UserRoleId) object;
    return Objects.equals(username, that.username) && Objects.equals(role, that.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, role);
  }
}
