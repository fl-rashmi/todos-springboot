package com.rashhworld.todo.controller;

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
    public ResponseEntity<String> createTodo(@RequestBody TodoEntity data) {
        todoService.createTodo(data);
        return new ResponseEntity<>("Todo added successfully.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllTodos() {
        List<TodoEntity> data = todoService.getAllTodos();

        if (data == null)
            return new ResponseEntity<>("No Todos found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable ObjectId id) {
        TodoEntity data = todoService.getSingleTodo(id);

        if (data == null)
            return new ResponseEntity<>("Todo not found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable ObjectId id, @RequestBody TodoEntity newData) {
        TodoEntity data = todoService.getSingleTodo(id);

        if (data == null)
            return new ResponseEntity<>("Todo not found", HttpStatus.NOT_FOUND);

        todoService.updateTodo(id, newData);
        return new ResponseEntity<>("Todo updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable ObjectId id) {
        TodoEntity data = todoService.getSingleTodo(id);

        if (data == null)
            return new ResponseEntity<>("Todo not found", HttpStatus.NOT_FOUND);

        todoService.deleteTodo(id);
        return new ResponseEntity<>("Todo deleted successfully", HttpStatus.OK);
    }
}
