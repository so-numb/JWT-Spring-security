package com.example.JWT.jwt.repository;

import com.example.JWT.jwt.model.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends MongoRepository<Roles, String> {
}
