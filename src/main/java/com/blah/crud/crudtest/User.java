package com.blah.crud.crudtest;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String userName;
  private String passWord;
}
