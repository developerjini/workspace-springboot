package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @GetMapping
  public String hello() {
    return "hello spring boot";
  }

  @GetMapping("list")
  public List<?> list() {
    List<Map<String, Object>> list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    map.put("a", 1);
    map.put("b", 2);

    list.add(map);
    map = new HashMap<>();
    map.put("c", 1);
    map.put("d", 5);
    list.add(map);
    return list;
  }
}
