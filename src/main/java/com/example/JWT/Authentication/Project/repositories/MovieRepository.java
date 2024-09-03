package com.example.JWT.Authentication.Project.repositories;

import com.example.JWT.Authentication.Project.entities.MovieEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<MovieEntity, ObjectId> {
}
