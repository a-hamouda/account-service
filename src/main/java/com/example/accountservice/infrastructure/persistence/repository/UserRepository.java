package com.example.accountservice.infrastructure.persistence.repository;

import com.example.accountservice.infrastructure.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    boolean existsByEmailIgnoreCase(String email);
}
