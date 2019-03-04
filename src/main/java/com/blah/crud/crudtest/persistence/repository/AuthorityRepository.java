package com.blah.crud.crudtest.persistence.repository;

import com.blah.crud.crudtest.persistence.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_AUTHORITIES")
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
