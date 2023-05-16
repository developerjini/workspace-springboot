package com.developerjini.hello.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developerjini.hello.dto.ResponseDTO;
import com.developerjini.hello.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
  // @GetMapping
  // public ResponseEntity<?> testTodo() {
  // return ResponseEntity.ok().body("반환값");
  // }

  @Autowired
  private TodoService service;

  @GetMapping
  public ResponseEntity<?> testTodo() {
    List<String> list = new ArrayList<>();
    list.add(service.testService());
    list.add(service.testService());
    list.add(service.testService());
    list.add(service.testService());
    return ResponseEntity.ok().body(ResponseDTO.<String>builder().data(list).build());

  }
}
