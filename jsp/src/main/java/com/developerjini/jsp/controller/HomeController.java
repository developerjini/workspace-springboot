package com.developerjini.jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class HomeController {
  public String home(Model model) {
    model.addAttribute("name", "myName");
    return "index";
  }
}
