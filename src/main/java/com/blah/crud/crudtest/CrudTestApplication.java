package com.blah.crud.crudtest;

import com.blah.crud.crudtest.authuser.UserDetailsServiceImpl;
import com.blah.crud.crudtest.persistence.repository.ApplicationUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = JmxAutoConfiguration.class)
public class CrudTestApplication {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsServiceImpl userDetailsService(ApplicationUserRepository applicationUserRepository) {
		return new UserDetailsServiceImpl(applicationUserRepository);
	}

	//@Bean
	//public ApplicationUserRepository applicationUserRepository() { return new ApplicationUserRepository();}

	public static void main(String[] args) {
		SpringApplication.run(CrudTestApplication.class, args);
	}

}
