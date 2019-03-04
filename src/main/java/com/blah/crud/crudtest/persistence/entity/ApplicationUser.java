package com.blah.crud.crudtest.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "application_user")
public class ApplicationUser implements UserDetails, Serializable {

  protected ApplicationUser() {}

  public ApplicationUser(String username, String password, String authority) {
    this.setUsername(username);
    this.setAuthorities(authority);
    this.setPassword(password);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private long id;

  //private String username;

  //private String password;
  //begin oddity

  @Column(name = "USER_NAME")
  private String username;

  @Column(name = "PASSWORD")
  private String password;

  /*
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "USERS_AUTHORITIES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
  @OrderBy
  @JsonIgnore
  */
  @Column(name = "AUTHORITY")
  private String authorities;

  @Column(name = "ACCOUNT_EXPIRED")
  private boolean accountExpired;

  @Column(name = "ACCOUNT_LOCKED")
  private boolean accountLocked;

  @Column(name = "CREDENTIALS_EXPIRED")
  private boolean credentialsExpired;

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Column(name = "ENABLED")
  private boolean enabled;

  public void setAccountExpired(boolean accountExpired) {
    this.accountExpired = accountExpired;
  }

  public boolean isAccountExpired() {
    return accountExpired;
  }

  public boolean isAccountLocked() {
    return accountLocked;
  }

  public void setAccountLocked(boolean accountLocked) {
    this.accountLocked = accountLocked;
  }



  public boolean isCredentialsExpired() {
    return credentialsExpired;
  }

  public void setCredentialsExpired(boolean credentialsExpired) {
    this.credentialsExpired = credentialsExpired;
  }


  @Override
  public Collection<Authority> getAuthorities() {
    Authority[] authArray = new Authority[] {new Authority(authorities)};
    return Arrays.asList(authArray);
  }

  public void setAuthorities(String authorities) {
    this.authorities = authorities;
  }


  @Override
  public boolean isAccountNonExpired() {
    return !isAccountExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return !isAccountLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !isCredentialsExpired();
  }
  //end oddity

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
