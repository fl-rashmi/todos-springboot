package com.rashhworld.todo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpringResponse {
    private String status;
    private String message;
    private Object data;
}
