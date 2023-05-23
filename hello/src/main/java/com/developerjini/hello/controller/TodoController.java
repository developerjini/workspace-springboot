package com.developerjini.hello.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.developerjini.hello.dto.ResponseDTO;
import com.developerjini.hello.dto.TodoDTO;
import com.developerjini.hello.model.TodoEntity;
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

  @GetMapping("test")
  public ResponseEntity<?> testTodo() {
    String str = service.testService(); // 테스트 서비스 사용
    List<String> list = new ArrayList<>();
    // list.add(service.testService());
    // list.add(service.testService());
    // list.add(service.testService());
    // list.add(service.testService());
    list.add(str);
    ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
    try {
      // String temporaryUserId = "temporary-user"; // temporary user id;

      // (1) TodoEntity로 변환한다.
      TodoEntity entity = TodoDTO.toEntity(dto);

      // (2) id를 null로 초기화한다. 생성 당시에는 id가 없어야하기 때문이다.
      entity.setId(null);

      // (3) 임시 유저 아이디를 설정해준다. 이부분은 인증과 인가에서 수정할 예정
      // 지금은 인증과 인가 기능이 없으므로 한 유저(temporary-user)만 로그인 없이 사용 가능한 애플리케이션인 셈
      entity.setUserId(userId);

      // (4) 서비스를 이용해 Todo 엔티티를 생성한다.
      List<TodoEntity> entities = service.create(entity);

      // (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
      // List<TodoDTO> dtos =
      // entities.stream().map(TodoDTO::new).collect(Collectors.toList());
      // 위 코드랑 같은 내용
      List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

      // (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

      // (7) ResponseDTO를 리턴한다.
      return ResponseEntity.ok().body(response);

    } catch (Exception e) {
      // (8) 혹시 예외가 나는 경우 dto 대신 error에 메세지를 넣어 리턴한다.
      String error = e.getMessage();
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);
    }
  }

  @GetMapping
  public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
    // String temporaryUserId = "temporary-user"; // temporary user id

    // (1) 서비스 메서드의 retrieve 메서드를 사용해 Todo 리스트를 가져온다.
    List<TodoEntity> entities = service.retrieve(userId);

    // (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

    // (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

    // (7) ResponseDTO를 리턴한다.
    return ResponseEntity.ok().body(response);
  }

  @PutMapping
  public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId) {
    // String temporaryUserId = "temporary-user";

    // (1) dto를 entity로 변환한다.
    TodoEntity entity = TodoDTO.toEntity(dto);

    // (2) id를 temporaryUserId로 초기화한다. 인증과 인가에서 수정할 예정
    entity.setUserId(userId);

    // (3) 서비스를 이용해 Entity를 업데이트 한다.
    List<TodoEntity> entities = service.update(entity);

    // (4) 자바스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

    // (5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

    // (6) ResponseDTO를 리턴한다.
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId) {
    try {
      // String temporaryUserId = "temporary-user";

      // (1) Todoentity로 변환한다.
      TodoEntity entity = TodoDTO.toEntity(dto);

      // (2) 임시유저 아이디를 설정해준다. 인증과 인가에서 수정할 예정
      // 지금은 인증과 인가 기능이 없으므로 한 유저(temporary-user)만 로그인 없이 사용 가능한 애플리케이션
      entity.setUserId(userId);

      // (3) 서비스를 이용해 entity를 삭제한다.
      List<TodoEntity> entities = service.delete(entity);

      // (4) 자바스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
      List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

      // (5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

      // (6) ResponseDTO를 리턴한다.
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      // (7) 혹시 예외가 나는 경우 dto 대신 error에 메세지를 넣어 리턴한다.
      String error = e.getMessage();
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);
    }

  }
}
