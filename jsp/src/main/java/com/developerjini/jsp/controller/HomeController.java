package com.developerjini.jsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.developerjini.jsp.service.HomeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

  @Autowired
  private HomeService homeService;

  @GetMapping("/")
  public String home(Model model) {
    log.info("home controller");
    model.addAttribute("name", "myName");
    model.addAttribute("time", homeService.getTime());
    return "index";
  }

  @GetMapping("list")
  public void getList(Model model) {
    model.addAttribute("list", homeService.getList());
  }}

