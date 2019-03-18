package com.blah.crud.crudtest.persistence.repository;

import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

//@Entity
//@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

/*    @Query("SELECT DISTINCT user_name FROM application_user " +

            "INNER JOIN FETCH user.authorities AS authorities " +

            "WHERE application_user.user_name = :username")*/
    //ApplicationUser findDistinctByUsername(String username);
    ApplicationUser findByUsername(String username);

    boolean existsByUsername(String username);
}
