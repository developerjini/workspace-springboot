package com.developerjini.jsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developerjini.jsp.mapper.HomeMapper;

@Service
public class HomeService {
  @Autowired
  private HomeMapper homeMapper;

  public String getTime() {
    return homeMapper.getTime();
  }
}
