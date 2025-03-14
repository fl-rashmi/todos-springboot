package com.rashhworld.todo.controller;

import com.rashhworld.todo.SpringResponse;
import com.rashhworld.todo.entity.TodoEntity;
import com.rashhworld.todo.service.TodoService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<SpringResponse> createTodo(@RequestBody TodoEntity data) {
        todoService.createTodo(data);
        SpringResponse response = SpringResponse.builder().status("success").message("Todo added successfully.").build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SpringResponse> getAllTodos() {
        List<TodoEntity> data = todoService.getAllTodos();
        SpringResponse response = SpringResponse.builder().status("success").data(data).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpringResponse> getSingleTodo(@PathVariable ObjectId id) {
        TodoEntity data = todoService.getSingleTodo(id);

        if (data == null) {
            SpringResponse response = SpringResponse.builder().status("error").message("Todo not found.").build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        SpringResponse response = SpringResponse.builder().status("success").data(data).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpringResponse> updateTodo(@PathVariable ObjectId id, @RequestBody TodoEntity newData) {
        TodoEntity data = todoService.getSingleTodo(id);

        if (data == null) {
            SpringResponse response = SpringResponse.builder().status("error").message("Todo not found.").build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        todoService.updateTodo(id, newData);
        SpringResponse response = SpringResponse.builder().status("success").message("Todo updated successfully.").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SpringResponse> deleteTodo(@PathVariable ObjectId id) {
        TodoEntity data = todoService.getSingleTodo(id);

        if (data == null) {
            SpringResponse response = SpringResponse.builder().status("error").message("Todo not found.").build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        todoService.deleteTodo(id);
        SpringResponse response = SpringResponse.builder().status("success").message("Todo deleted successfully.").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}