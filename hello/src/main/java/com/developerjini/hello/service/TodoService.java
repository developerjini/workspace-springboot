package com.developerjini.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developerjini.hello.model.TodoEntity;
import com.developerjini.hello.persistence.TodoRepository;

@Service
public class TodoService {
  // public String testService() {
  // return "test Service";
  // }

  @Autowired
  private TodoRepository repository;

  public String testService() {
    // TodoEntity 생성
    TodoEntity entity = TodoEntity.builder().title("My First todo item").build();
    // TodoEntity 저장
    repository.save(entity);
    // TodoEntity 검색
    TodoEntity savedEntity = repository.findById(entity.getId()).get();
    return savedEntity.getTitle();
  }

}
