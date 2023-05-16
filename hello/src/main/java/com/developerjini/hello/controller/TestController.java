package com.developerjini.hello.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.developerjini.hello.dto.ResponseDTO;
import com.developerjini.hello.dto.TestRequsetBodyDTO;

@RestController
@RequestMapping("test") // 리소스
public class TestController {
  @GetMapping
  public String testController() {
    return "hello world";
  }

  @GetMapping("testGetMapping")
  public String testControllerWithPath() {
    return "Hello World! testGetMapping";
  }

  @GetMapping("{id}")
  public String testControllerWithPathVariables(@PathVariable String id) {
    return "Hello world : " + id;
  }

  @GetMapping("requestParam")
  public String testControllerRequestParam(@RequestParam(required = false) int id) {
    return "Hello World ID : " + id;
  }

  @GetMapping("requestBody")
  public String testControllerRequestBody(@RequestBody TestRequsetBodyDTO testRequsetBodyDTO) {
    return "Hello World Id : " + testRequsetBodyDTO.getId() + ", message : " + testRequsetBodyDTO.getMessage();
  }

  @GetMapping("requestDTO")
  public String testControllerDTO(TestRequsetBodyDTO testRequsetBodyDTO) {
    return "Hello World Id : " + testRequsetBodyDTO.getId() + ", message : " + testRequsetBodyDTO.getMessage();
  }

  // 반환 테스트
  @GetMapping("testResponseBody")
  public ResponseDTO<String> testControllerResponseBody() {
    List<String> list = new ArrayList<>();
    list.add("hello world I'm responseDTO");
    ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
    return responseDTO;
  }

  @GetMapping("testResponseEntity")
  public ResponseEntity<?> testControllerResponseEntity() {
    List<String> list = new ArrayList<>();
    list.add("Hello World! I'm ResponseEntity. And you got 400!");
    ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
    // http status 400 으로 설정
    return ResponseEntity.badRequest().body(response);
  }
}
