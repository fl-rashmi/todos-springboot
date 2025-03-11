package com.rashhworld.todo.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.rashhworld.todo.entity.TodoEntity;

public interface TodoRepository extends MongoRepository<TodoEntity, ObjectId> {
    List<TodoEntity> findByUserId(ObjectId userId);

    Optional<TodoEntity> findByIdAndUserId(ObjectId id, ObjectId userId);
}
