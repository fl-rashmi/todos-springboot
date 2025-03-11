package com.rashhworld.todo.service;

import com.rashhworld.todo.entity.TodoEntity;
import com.rashhworld.todo.entity.UserEntity;
import com.rashhworld.todo.repository.TodoRepository;
import com.rashhworld.todo.repository.UserRepository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private ObjectId getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    public void createTodo(TodoEntity data) {
        data.setUserId(getCurrentUserId());
        todoRepository.save(data);
    }

    public List<TodoEntity> getAllTodos() {
        ObjectId userId = getCurrentUserId();
        return todoRepository.findByUserId(userId);
    }

    public TodoEntity getSingleTodo(ObjectId id) {
        ObjectId userId = getCurrentUserId();
        return todoRepository.findByIdAndUserId(id, userId).orElse(null);
    }

    public void updateTodo(ObjectId id, TodoEntity data) {
        TodoEntity existingTodo = todoRepository.findById(id).orElse(null);

        existingTodo.setTitle(data.getTitle());
        existingTodo.setDescription(data.getDescription());
        todoRepository.save(existingTodo);
    }

    public void deleteTodo(ObjectId id) {
        todoRepository.deleteById(id);
    }
}
