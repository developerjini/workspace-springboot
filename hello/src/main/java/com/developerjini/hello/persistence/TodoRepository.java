package com.developerjini.hello.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developerjini.hello.model.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

}
