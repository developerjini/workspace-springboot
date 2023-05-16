package com.developerjini.hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developerjini.hello.model.TodoEntity;
import com.developerjini.hello.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

  public List<TodoEntity> create(final TodoEntity entity) {
    // Validations
    validate(entity);

    repository.save(entity);

    log.warn("Entity Id : {} is saved", entity.getId());

    return repository.findByUserId(entity.getUserId());
  }

  private void validate(final TodoEntity entity) {

    if (entity == null) {
      log.warn("Entity cannot be null.");
      throw new RuntimeException("Entity cannot be null");
    }

    if (entity.getUserId() == null) {
      log.warn("Unknown User");
      throw new RuntimeException("Unknown user");
    }
  }
}
