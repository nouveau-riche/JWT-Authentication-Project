package com.example.JWT.Authentication.Project.repositories;

import com.example.JWT.Authentication.Project.entities.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

}
