package com.example.accountservice.application;

import com.example.accountservice.infrastructure.persistence.entity.User;
import com.example.accountservice.infrastructure.persistence.repository.UserRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
@EntityScan(basePackageClasses = {User.class})
public class PersistenceConfiguration {

}
