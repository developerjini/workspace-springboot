package com.developerjini.hello.service;

import java.util.List;
import java.util.Optional;

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

  // 검색 서비스 구현
  public List<TodoEntity> retrieve(final String userId) {
    return repository.findByUserId(userId);
  }

  // 수정 서비스 구현
  public List<TodoEntity> update(final TodoEntity entity) {
    // (1) 저장할 엔티티가 유효한지 확인한다. 이 메서드는 Create Todo에서 구현했다.
    validate(entity);

    // (2) 넘겨 받은 엔티티 id를 이용해 TodoEntity를 가져온다. 존재하지 않는 엔티티는 업데이트할 수 없기 때문
    final Optional<TodoEntity> original = repository.findById(entity.getId());

    original.ifPresent(todo -> {
      // (3) 반환된 TodoEntity가 존재하면 값을 새 entity의 값으로 덮어 씌운다.
      todo.setTitle(entity.getTitle());
      todo.setDone(entity.isDone());

      // (4) 데이터베이스에 새 값을 지정한다.
      repository.save(todo);
    });
    // TodoEntity original = repository.findById(entity.getId()).get(); //
    // (2),(3),(4)코드가 이 줄로 대체 가능

    // Retrieve Todo에서 만든 메서드를 이용해 유저의 모든 Todo 리스트를 리턴한다.
    return retrieve(entity.getUserId());
  }

  public List<TodoEntity> delete(final TodoEntity entity) {
    // (1) 저장할 엔티티가 유효한지 확인한다. 이 메서드는 Create Todo에서 구현했다.
    validate(entity);
    try {
      // (2) entity를 삭제한다.
      repository.delete(entity);
    } catch (Exception e) {
      // (3) exception 발생시 id와 exception을 로깅한다.
      log.error("error deleting entity", entity.getId(), e);

      // (4) 컨트롤러로 exception을 날린다. 데이터베이스 내부 로직을 캡슐화 하기 위해 e를 리턴하지 않고 새 exception
      // 오브젝트를 리턴한다.
      throw new RuntimeException("error deleting entity" + entity.getId());
    }
    // (5) 새 Todo 리스트를 가져와 리턴한다.
    return retrieve(entity.getUserId());
  }
}
