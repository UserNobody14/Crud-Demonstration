package com.blah.crud.crudtest.authuser;

import javax.persistence.*;

@Entity
@Table(name = "ApplicationUser")
public class ApplicationUser {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String username;

  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
